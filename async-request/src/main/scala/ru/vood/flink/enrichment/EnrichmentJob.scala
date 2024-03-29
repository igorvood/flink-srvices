package ru.vood.flink.enrichment

import org.apache.flink.api.java.functions.KeySelector
import org.apache.flink.streaming.api.datastream.DataStreamSink
import org.apache.flink.streaming.api.functions.sink.SinkFunction
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment, createTypeInformation}
import org.slf4j.LoggerFactory
import play.api.libs.json.{JsObject, JsValue, OWrites}
import ru.vood.flink.common.abstraction.FlinkStreamProducerPredef.{StreamExecutionEnvironmentPredef, StreamFactory}
import ru.vood.flink.common.base.EnrichFlinkDataStream.EnrichFlinkDataStreamSink
import ru.vood.flink.common.dto.UaspDto
import ru.vood.flink.common.extension.CommonExtension.Also
import ru.vood.flink.common.kafka.FlinkSinkProperties
import ru.vood.flink.common.kafka.FlinkSinkProperties.producerFactoryDefault
import ru.vood.flink.common.service.JsonConvertOutService.{IdentityPredef, JsonPredef}
import ru.vood.flink.common.service.{JsonConvertOutService, UaspDeserializationProcessFunction}
import ru.vood.flink.common.service.dto.{KafkaDto, OutDtoWithErrors}
import ru.vood.flink.enrichment.service.JsValueConsumer
import ru.vood.flink.enrichment.service.dto.{FlinkDataStreams, KeyedCAData, KeyedUasp, OutStreams}
import ru.vood.flink.enrichment.utils.config.MDMEnrichmentPropsModel.appPrefixDefaultName
import ru.vood.flink.enrichment.utils.config._


object EnrichmentJob extends Serializable {

  val keySelectorMain: KeySelector[KeyedUasp, String] =
    (in: KeyedUasp) => {
      in.localId
    }

  val keySelectorCa: KeySelector[KeyedCAData, String] =
    (in: KeyedCAData) => {
      in.key
    }


  private val logger = LoggerFactory.getLogger(getClass)

  def main(args: Array[String]): Unit = {
    logger.info("Start app: " + this.getClass.getName)
    try {
      val propsModel = MDMEnrichmentPropsModel.configApp(appPrefixDefaultName, args)

      val env = StreamExecutionEnvironment.getExecutionEnvironment
      env.setParallelism(propsModel.appSyncParallelism)
      //      env.enableCheckpointing(propsModel.initProperty.appStreamCheckpointTimeMilliseconds.value, CheckpointingMode.EXACTLY_ONCE)


      val flinkDataStreams = init(env, propsModel)

      val mainDataStream = process(
        flinkDataStreams = flinkDataStreams,
        propsModel)

      setSinks(mainDataStream, propsModel)

      env.execute(propsModel.serviceData.fullServiceName)

    } catch {
      case e: Exception =>
        e.printStackTrace()
        logger.error("Error:" + e.getMessage)
        System.exit(1)
    }
  }

  def init(env: StreamExecutionEnvironment, propsModel: MDMEnrichmentPropsModel, producerFabric: FlinkSinkProperties => SinkFunction[KafkaDto] = producerFactoryDefault): FlinkDataStreams = {


    implicit val serviceData = propsModel.serviceData

    val uaspDeserializationProcessFunction = UaspDeserializationProcessFunction()

    val serialisationProcessFunctionJsValue = new JsValueConsumer(serviceData)

    val mainDataStream = env
      .registerConsumerWithMetric(
        propsModel.serviceData,
        propsModel.allEnrichProperty.mainEnrichProperty.fromTopic,
        propsModel.allEnrichProperty.mainEnrichProperty.dlqTopicProp,
        uaspDeserializationProcessFunction,
        producerFabric)


    val commonStream = propsModel.allEnrichProperty.commonEnrichProperty
      .map { cns =>
        val commonEnrichPropertyDlq = propsModel.allEnrichProperty.commonEnrichProperty.flatMap(a => a.dlqTopicProp)
        env
          .registerConsumerWithMetric(
            propsModel.serviceData,
            cns.fromTopic,
            commonEnrichPropertyDlq, serialisationProcessFunctionJsValue, producerFabric)
      }


    val globalIdStream = propsModel.allEnrichProperty.globalIdEnrichProperty
      .map { cns =>
        val globalIdEnrichPropertyDlq = propsModel.allEnrichProperty.globalIdEnrichProperty.flatMap(a => a.dlqTopicProp)
        env.registerConsumerWithMetric(
          propsModel.serviceData,
          cns.fromTopic,
          globalIdEnrichPropertyDlq,
          serialisationProcessFunctionJsValue,
          producerFabric)
      }

    FlinkDataStreams(mainDataStream, commonStream, globalIdStream)
  }

  /**
   * Заставили под пытками сделать подобный метод, даже боюсь словами описывать его логику.
   */
  def process(flinkDataStreams: FlinkDataStreams,
              mDMEnrichmentPropsModel: MDMEnrichmentPropsModel,
              producerFabric: FlinkSinkProperties => SinkFunction[KafkaDto] = producerFactoryDefault
             ): OutStreams = {

    //    implicit val value1: OWrites[JsValue] = Json.writes[JsValue]

    implicit val value1: OWrites[JsValue] = new OWrites[JsValue] {
      override def writes(o: JsValue): JsObject = o.asInstanceOf[JsObject]
    }


    //    implicit val value11 = ru.vood.flink.common.service.dto.OutDtoWithErrors.outDtoWithErrorsJsonWrites[JsValue]


    val mainDlqProp = mDMEnrichmentPropsModel.allEnrichProperty.mainEnrichProperty.dlqTopicProp

    val streamGlobal = flinkDataStreams.mainDataStream
      // если настроено обогащение глобальнымнтификатором, то надо обогатить
      .also { mainDs =>
        val maybeGlobal = for {
          keyedMainStreamSrv <- mDMEnrichmentPropsModel.globalMainStreamExtractKeyFunction
          keyGlobalSrv <- mDMEnrichmentPropsModel.keyGlobalIdEnrichmentMapService
          validateGlobalIdService <- mDMEnrichmentPropsModel.validateGlobalIdService
          globalIdStream <- flinkDataStreams.globalIdStream
        } yield (keyedMainStreamSrv, validateGlobalIdService, keyGlobalSrv, globalIdStream)
        maybeGlobal
          .map { tuple =>
            val (keyedMainStreamSrv, validateGlobalIdService, keyGlobalSrv, globalIdStream) = tuple
            val dlqGlobalIdProp = mDMEnrichmentPropsModel.allEnrichProperty.globalIdEnrichProperty.flatMap(a => a.dlqTopicProp)

            val validatedGlobalIdStream = globalIdStream
              .processWithMaskedDqlF(
                mDMEnrichmentPropsModel.serviceData,
                validateGlobalIdService,
                dlqGlobalIdProp.map(sp => sp -> { (q, w) => q.serializeToBytes(w) }),
//                dlqGlobalIdProp.map(sp => sp -> { (q, w) => JsonConvertOutService.serializeToBytes[OutDtoWithErrors[JsValue]](q, w)(OutDtoWithErrors.) }),
//                dlqGlobalIdProp.map(sp => sp -> JsonConvertOutService.serializeToBytes[OutDtoWithErrors[JsValue]]),
                producerFabric)

            mainDs
              .processWithMaskedDqlF(
                mDMEnrichmentPropsModel.serviceData,
                keyedMainStreamSrv,
                mainDlqProp.map(sp => sp -> { (q, w) => q.serializeToBytes(w) }),
                producerFabric)
              .keyBy(keySelectorMain)
              .connect(validatedGlobalIdStream.keyBy(d => d.key))
              .process(keyGlobalSrv)
              .processWithMaskedDqlFC[UaspDto, UaspDto](
                mDMEnrichmentPropsModel.serviceData,
                mDMEnrichmentPropsModel.throwToDlqService,
                mainDlqProp.map(sp => sp -> { (q, w) => q.serializeToBytes(w) }),
                producerFabric
              )
          }.getOrElse(mainDs)
      }
    val streamCommon = streamGlobal
      // если настроено обогащение, то надо обогатаить. Тут меняются только мапки, поле id Не трогается
      .also { mainDs =>
        val maybeCommon = for {
          keyedMainStreamSrv <- mDMEnrichmentPropsModel.commonMainStreamExtractKeyFunction
          keyCommonEnrichmentMapService <- mDMEnrichmentPropsModel.keyCommonEnrichmentMapService
          commonValidateProcessFunction <- mDMEnrichmentPropsModel.commonValidateProcessFunction
          commonStream <- flinkDataStreams.commonStream
        } yield (keyedMainStreamSrv, keyCommonEnrichmentMapService, commonValidateProcessFunction, commonStream)

        maybeCommon
          .map { tuple =>
            val (keyedMainStreamSrv, keyCommonEnrichmentMapService, commonValidateProcessFunction, commonStream) = tuple
            val dlqGlobalIdProp = mDMEnrichmentPropsModel.allEnrichProperty.commonEnrichProperty.flatMap(a => a.dlqTopicProp)

            val validatedGlobalIdStream = commonStream
              .processWithMaskedDqlF(
                mDMEnrichmentPropsModel.serviceData,
                commonValidateProcessFunction,
                dlqGlobalIdProp.map(sp => sp -> { (q, w) => q.serializeToBytes(w) }), producerFabric)

            mainDs
              .processWithMaskedDqlF(
                mDMEnrichmentPropsModel.serviceData,
                keyedMainStreamSrv,
                mainDlqProp.map(sp => sp -> { (q, w) => q.serializeToBytes(w) }), producerFabric)
              .keyBy(keySelectorMain)
              .connect(validatedGlobalIdStream.keyBy(d => d.key))
              .process(keyCommonEnrichmentMapService)
              .processWithMaskedDqlFC[UaspDto, UaspDto](
                mDMEnrichmentPropsModel.serviceData,
                mDMEnrichmentPropsModel.throwToDlqService,
                mainDlqProp.map(sp => sp -> { (q, w) => q.serializeToBytes(w) }),
                producerFabric
              )
          }.getOrElse(mainDs)
      }


    OutStreams(streamCommon)


  }

  def setSinks(outStreams: OutStreams,
               syncProperties: MDMEnrichmentPropsModel): Unit = {
    setMainSink(outStreams.mainStream, syncProperties)

  }

  def setMainSink(mainDataStream: DataStream[UaspDto],
                  syncProperties: MDMEnrichmentPropsModel,
                  producerFabric: FlinkSinkProperties => SinkFunction[KafkaDto] = producerFactoryDefault
                 ): DataStreamSink[KafkaDto] = {
    val mainProducer = syncProperties.flinkSinkPropertiesMainProducer.createSinkFunction(producerFabric)


    mainDataStream
      .map(_.serializeToBytes(None).right.get)
      .map(syncProperties.flinkSinkPropertiesMainProducer.prometheusMetric[KafkaDto](syncProperties.serviceData))
      .addSink(mainProducer)
      .enrichName(s"MAIN_SINK_outEnrichmentSink")
  }


}
