package ru.vood.flink.common.abstraction

import org.apache.flink.api.scala.createTypeInformation
import org.apache.flink.streaming.api.scala.DataStream
import org.scalatest.flatspec.AnyFlatSpec
import play.api.libs.json.OFormat.oFormatFromReadsAndOWrites
import play.api.libs.json.{JsValue, Json}
import ru.vood.flink.common.service.dto.OutDtoWithErrors
import ru.vood.flink.common.abstraction.FlinkKafkaFunTest._
import ru.vood.flink.common.abstraction.FlinkStreamProducerPredef.StreamFactory
import ru.vood.flink.common.test.MiniPipeLineTrait.valuesTestDataDto
import ru.vood.flink.common.kafka.FlinkSinkProperties
import ru.vood.flink.common.mask.MaskedPredef.PathFactory
import ru.vood.flink.common.mask.MaskedStrPathWithFunName
import ru.vood.flink.common.service.JsonConvertInService
import ru.vood.flink.common.service.JsonConvertOutService.serializeToBytes
import ru.vood.flink.common.service.dto.{KafkaDto, OutDtoWithErrors, ServiceDataDto}
import ru.vood.flink.common.test.MiniPipeLineTrait
import ru.vood.flink.common.utils.config.kafka.KafkaPrdProperty

import java.util.Properties
import scala.collection.JavaConverters.mapAsJavaMapConverter

class FlinkKafkaFunTest extends AnyFlatSpec with MiniPipeLineTrait with Serializable {


  "NewFlinkStreamPredef.createProducerWithMetric " should " OK" in {

    val flinkPipe: DataStream[TestDataDto] => Unit = { ds =>
      FlinkKafkaFun.privateCreateProducerWithMetric(ds, serviceData = serviceDataDto, flinkSinkProperties, producerFactory)
    }

    pipeRun(listTestDataDto, flinkPipe)

    assertResult(1)(valuesTestDataDto.size)
    val dtoes = topicDataArray[TestDataDto](flinkSinkProperties)
    assertResult(1)(dtoes.size)
  }

  "NewFlinkStreamPredef.processAndDlqSinkWithMetric Ошибка, но маскирование не настроено" should " OK" in {

    val flinkPipe: DataStream[TestDataDto] => Unit = { ds =>
      val value = FlinkKafkaFun.processAndDlqSinkWithMetric[TestDataDto, TestDataDto](
        ds,
        serviceData = serviceDataDto,
        dlqProcessFunctionError,
        Some(flinkSinkPropertiesDlq -> serializeToBytes[OutDtoWithErrors[TestDataDto]]),
        producerFactory[KafkaDto],
      )
    }

    processWithMaskedDqlErrNoMasked(flinkPipe)
  }

  "processWithMaskedDqlF Ошибка, но маскирование не настроено" should " OK" in {

    val flinkPipe: DataStream[TestDataDto] => Unit = { ds =>
      val value1 = ds.processWithMaskedDqlF(
        serviceDataDto,
        dlqProcessFunctionError,
        Some(flinkSinkPropertiesDlq -> serializeToBytes[OutDtoWithErrors[TestDataDto]]),
        producerFactory[KafkaDto]
      )
    }

    processWithMaskedDqlErrNoMasked(flinkPipe)
  }


  "processWithMaskedDqlF jsValue Ошибка, но маскирование не настроено" should " OK" in {

    val values = listTestDataDto
      .map(d => Json.toJson(d))

    val flinkPipe: DataStream[JsValue] => Unit = { ds =>

      val value1 = ds.processWithMaskedDqlF(
        serviceDataDto,
        dlqProcessFunctionErrorJs,
        Some(flinkSinkPropertiesDlq -> { (q, w) => {
          implicit val value2 = OutDtoWithErrors.writesJsValue
          implicit val value = OutDtoWithErrors.outDtoWithErrorsJsonWrites[JsValue](value2)

          serializeToBytes[OutDtoWithErrors[JsValue]](q, w)(value)
        }
        }),
        producerFactory[KafkaDto]
      )
    }


    pipeRun(values, flinkPipe)

    assertResult(1)(valuesTestDataDto.size)

    val dtoes1 = topicDataArray[TestDataDto](flinkSinkProperties)
    assertResult(0)(dtoes1.size)

    val dtoes = topicDataArray[KafkaDto](flinkSinkPropertiesDlq)
    assertResult(1)(dtoes.size)
    val either = JsonConvertInService.deserialize[OutDtoWithErrors[TestDataDto]](dtoes.head.value)(OutDtoWithErrors.outDtoWithErrorsJsonReads, serviceDataDto)
    val unit = outDtoWithErrorsFun(Some(testDataDto))
    assertResult(unit)(either.right.get)



    //    val flinkPipe: DataStream[TestDataDto] => Unit = { ds =>
    //      implicit val value2 = OutDtoWithErrors.outDtoWithErrorsJsonWrites[JsValue](OutDtoWithErrors.writesJsValue)
    //
    //      val value = ds.map(q =>
    //        Json.toJsObject(q).asInstanceOf[JsValue]
    //      )
    //      val value1 = value.processWithMaskedDqlF(
    //        serviceDataDto,
    //        dlqProcessFunctionErrorJs,
    //        Some(flinkSinkPropertiesDlq -> JsonConvertOutService.serializeToBytes[OutDtoWithErrors[JsValue]]),
    //        producerFactory[KafkaDto]
    //      )
    //    }
    //
    //    processWithMaskedDqlErrNoMasked(flinkPipe)
  }

  "processWithMaskedDql1 Ошибка, но маскирование не настроено" should " OK" in {
    val flinkPipe: DataStream[TestDataDto] => Unit = { ds =>
      val value1 = ds.processWithMaskedDqlFC(
        serviceDataDto,
        dlqProcessFunctionError,
        Some(flinkSinkPropertiesDlq -> { (q, w) => serializeToBytes[OutDtoWithErrors[TestDataDto]](q, w) }),
        producerFactory[KafkaDto]
      )
    }

    processWithMaskedDqlErrNoMasked(flinkPipe)
  }

  "processWithMaskedDql Ошибка, но маскирование не настроено" should " OK" in {

    val flinkPipe: DataStream[TestDataDto] => Unit = { ds =>
      val service = new TestDataDtoMaskedSerializeServiceDLQ(jsMaskedPath = None)
      val value1 = ds.processWithMaskedDql(serviceDataDto, dlqProcessFunctionError, Some(flinkSinkPropertiesDlq -> service), producerFactory[KafkaDto])
    }

    processWithMaskedDqlErrNoMasked(flinkPipe)
  }

  private def processWithMaskedDqlErrNoMasked(flinkPipe: DataStream[TestDataDto] => Unit) = {
    pipeRun(listTestDataDto, flinkPipe)

    assertResult(1)(valuesTestDataDto.size)

    val dtoes1 = topicDataArray[TestDataDto](flinkSinkProperties)
    assertResult(0)(dtoes1.size)

    val dtoes = topicDataArray[KafkaDto](flinkSinkPropertiesDlq)
    assertResult(1)(dtoes.size)
    val either = JsonConvertInService.deserialize[OutDtoWithErrors[TestDataDto]](dtoes.head.value)(OutDtoWithErrors.outDtoWithErrorsJsonReads, serviceDataDto)
    val unit = outDtoWithErrorsFun(Some(testDataDto))
    assertResult(unit)(either.right.get)
  }

  "NewFlinkStreamPredef.processAndDlqSinkWithMetric нет ошибки, но маскирование не настроено" should " OK" in {

    val flinkPipe: DataStream[TestDataDto] => Unit = { ds =>


      val value = FlinkKafkaFun.processAndDlqSinkWithMetric[TestDataDto, TestDataDto](
        ds,
        serviceData = serviceDataDto,
        dlqProcessFunction,
        Some(flinkSinkPropertiesDlq -> serializeToBytes[OutDtoWithErrors[TestDataDto]]),
        producerFactory[KafkaDto],

      )

      FlinkKafkaFun.privateCreateProducerWithMetric(value, serviceData = serviceDataDto, flinkSinkProperties, producerFactory)
    }

    processWithMaskedDqlNoErrNoMasked(flinkPipe)

  }

  "processWithMaskedDqlF нет ошибки, но маскирование не настроено" should " OK" in {

    val flinkPipe: DataStream[TestDataDto] => Unit = { ds =>

      val value = ds.processWithMaskedDqlF(serviceDataDto, dlqProcessFunction, Some(flinkSinkPropertiesDlq -> serializeToBytes[OutDtoWithErrors[TestDataDto]]), producerFactory[KafkaDto])

      FlinkKafkaFun.privateCreateProducerWithMetric(value, serviceData = serviceDataDto, flinkSinkProperties, producerFactory)
    }

    processWithMaskedDqlNoErrNoMasked(flinkPipe)

  }

  "processWithMaskedDql нет ошибки, но маскирование не настроено" should " OK" in {

    val flinkPipe: DataStream[TestDataDto] => Unit = { ds =>

      val service = new TestDataDtoMaskedSerializeServiceDLQ(jsMaskedPathDLQOk)
      val value = ds.processWithMaskedDql(serviceDataDto, dlqProcessFunction, Some(flinkSinkPropertiesDlq -> service), producerFactory[KafkaDto])

      FlinkKafkaFun.privateCreateProducerWithMetric(value, serviceData = serviceDataDto, flinkSinkProperties, producerFactory)
    }

    processWithMaskedDqlNoErrNoMasked(flinkPipe)

  }


  private def processWithMaskedDqlNoErrNoMasked(flinkPipe: DataStream[TestDataDto] => Unit) = {
    pipeRun(listTestDataDto, flinkPipe)

    assertResult(1)(valuesTestDataDto.size)

    val dtoes1 = topicDataArray[TestDataDto](flinkSinkProperties)
    assertResult(1)(dtoes1.size)

    assertResult(testDataDto)(dtoes1.head)

    val dtoes = topicDataArray[KafkaDto](flinkSinkPropertiesDlq)
    assertResult(0)(dtoes.size)
  }

  "NewFlinkStreamPredef.processAndDlqSinkWithMetric Ошибка, маскирование настроено" should " OK" in {

    val flinkPipe: DataStream[TestDataDto] => Unit = { ds =>

      val value = FlinkKafkaFun.processAndDlqSinkWithMetric[TestDataDto, TestDataDto](
        ds,
        serviceData = serviceDataDto,
        dlqProcessFunctionError,
        Some(flinkSinkPropertiesDlq.copy(jsMaskedPath = jsMaskedPathDLQOk) -> serializeToBytes[OutDtoWithErrors[TestDataDto]]),
        producerFactory[KafkaDto],
      )
    }

    processWithMaskedDqlErrMasked(flinkPipe)

  }

  "processWithMaskedDqlF Ошибка, маскирование настроено" should " OK" in {

    val flinkPipe: DataStream[TestDataDto] => Unit = { ds =>
      val value1 = ds.processWithMaskedDqlF(serviceDataDto, dlqProcessFunctionError, Some(flinkSinkPropertiesDlq.copy(jsMaskedPath = jsMaskedPathDLQOk) -> serializeToBytes[OutDtoWithErrors[TestDataDto]]), producerFactory[KafkaDto])
    }

    processWithMaskedDqlErrMasked(flinkPipe)

  }

  "processWithMaskedDql Ошибка, маскирование настроено" should " OK" in {

    val service = new TestDataDtoMaskedSerializeServiceDLQ(jsMaskedPathDLQOk)
    val flinkPipe: DataStream[TestDataDto] => Unit = { ds =>
      val value1 = ds.processWithMaskedDql(serviceDataDto, dlqProcessFunctionError, Some(flinkSinkPropertiesDlq.copy(jsMaskedPath = jsMaskedPathDLQOk) -> service), producerFactory[KafkaDto])
    }

    processWithMaskedDqlErrMasked(flinkPipe)

  }

  private def processWithMaskedDqlErrMasked(flinkPipe: DataStream[TestDataDto] => Unit) = {
    pipeRun(listTestDataDto, flinkPipe)

    assertResult(1)(valuesTestDataDto.size)

    val dtoes1 = topicDataArray[TestDataDto](flinkSinkProperties)
    assertResult(0)(dtoes1.size)

    val dtoes = topicDataArray[KafkaDto](flinkSinkPropertiesDlq)
    assertResult(1)(dtoes.size)
    val outDto: OutDtoWithErrors[TestDataDto] = JsonConvertInService.deserialize[OutDtoWithErrors[TestDataDto]](dtoes.head.value)(OutDtoWithErrors.outDtoWithErrorsJsonReads, serviceDataDto).right.get
    val outDtoWith = outDtoWithErrorsFun(Some(testDataDto.copy(srt = "***MASKED***")))
    assertResult(outDtoWith)(outDto)
  }

  "maskedProducerF Ошибка при маскировании основного сообщения, маскирование DLQ без ошибки " should " OK" in {

    val flinkPipe: DataStream[TestDataDto] => Unit = { ds =>

      val value = ds.maskedProducerF(
        serviceDataDto,
        flinkSinkProperties.copy(jsMaskedPath = jsMaskedPathErrMainERR),
        serializeToBytes[TestDataDto],
        Some(flinkSinkPropertiesDlq.copy(jsMaskedPath = jsMaskedPathDLQOk) -> serializeToBytes[OutDtoWithErrors[TestDataDto]]),
        producerFactory[KafkaDto]
      )
    }

    maskedProducerFErrMainDLQOk(flinkPipe)
  }

  "maskedProducer Ошибка при маскировании основного сообщения, маскирование DLQ без ошибки " should " OK" in {
    val serviceJsMaskedPathDLQOk = new TestDataDtoMaskedSerializeServiceDLQ(jsMaskedPathDLQOk)
    val serializeServiceMain = new TestDataDtoMaskedSerializeServiceMain(jsMaskedPathErrMainERR)
    val flinkPipe: DataStream[TestDataDto] => Unit = { ds =>
      val value = ds.maskedProducer(
        serviceDataDto,
        flinkSinkProperties.copy(jsMaskedPath = jsMaskedPathErrMainERR),
        serializeServiceMain,
        Some(flinkSinkPropertiesDlq.copy(jsMaskedPath = jsMaskedPathDLQOk) -> serviceJsMaskedPathDLQOk),
        producerFactory[KafkaDto]
      )
    }

    maskedProducerFErrMainDLQOk(flinkPipe)
  }

  private def maskedProducerFErrMainDLQOk(flinkPipe: DataStream[TestDataDto] => Unit) = {
    pipeRun(listTestDataDto, flinkPipe)

    assertResult(1)(valuesTestDataDto.size)

    val kafkaDtos = topicDataArray[KafkaDto](flinkSinkPropertiesDlq)
    assertResult(1)(kafkaDtos.size)
    val outDto: OutDtoWithErrors[TestDataDto] = JsonConvertInService.deserialize[OutDtoWithErrors[TestDataDto]](kafkaDtos.head.value)(OutDtoWithErrors.outDtoWithErrorsJsonReads, serviceDataDto).right.get

    val outDtoWith = outDtoWithErrorsFun(Some(testDataDto.copy(srt = "***MASKED***")))
      .copy(
        errorPosition = outDto.errorPosition,
        errors = List(
          "createProducerWithMetric: Unable to mask dto ru.vood.flink.common.abstraction.TestDataDto. Masked rule Some(JsMaskedPathObject(Map(srt -> JsNumberMaskedPathValue(NumberMaskAll()))))",
          "Unable to masked value wrapper class  class play.api.libs.json.JsString with function -> class ru.vood.flink.common.mask.dto.JsNumberMaskedPathValue"
        )
      )
    assertResult(outDtoWith)(outDto)
  }

  "maskedProducerF Ошибка при маскировании основного сообщения, маскирование DLQ с ошибкой " should " OK" in {

    val flinkPipe: DataStream[TestDataDto] => Unit = { ds =>
      val value = ds.maskedProducerF(
        serviceDataDto,
        flinkSinkProperties.copy(jsMaskedPath = jsMaskedPathErrMainERR),
        serializeToBytes[TestDataDto],
        Some(flinkSinkPropertiesDlq.copy(jsMaskedPath = jsMaskedPathDLQErr) -> serializeToBytes[OutDtoWithErrors[TestDataDto]]),
        producerFactory[KafkaDto]
      )
    }
    pipeRun(listTestDataDto, flinkPipe)

    assertResult(1)(valuesTestDataDto.size)

    val kafkaDtos = topicDataArray[KafkaDto](flinkSinkPropertiesDlq)
    assertResult(1)(kafkaDtos.size)
    val outDto: OutDtoWithErrors[TestDataDto] = JsonConvertInService.deserialize[OutDtoWithErrors[TestDataDto]](kafkaDtos.head.value)(OutDtoWithErrors.outDtoWithErrorsJsonReads, serviceDataDto).right.get

    val outDtoWith = outDtoWithErrorsFun(Some(testDataDto.copy(srt = "***MASKED***")))
      .copy(
        errorPosition = outDto.errorPosition,
        errors = List(
          "processAndDlqSinkWithMetric: Unable to mask dto scala.collection.immutable.$colon$colon. Masked rule Some(JsMaskedPathObject(Map(data -> JsMaskedPathObject(Map(srt -> JsNumberMaskedPathValue(NumberMaskAll()))))))",
          "Unable to masked value wrapper class  class play.api.libs.json.JsString with function -> class ru.vood.flink.common.mask.dto.JsNumberMaskedPathValue"
        ),
        data = None
      )
    assertResult(outDtoWith)(outDto)
  }

  "maskedProducerF успех при маскировании основного сообщения, маскирование DLQ с ошибкой " should " OK" in {

    val flinkPipe: DataStream[TestDataDto] => Unit = { ds =>
      val value = ds.maskedProducerF(
        serviceDataDto,
        flinkSinkProperties.copy(jsMaskedPath = jsMaskedPathErrMainOk),
        serializeToBytes[TestDataDto],
        Some(flinkSinkPropertiesDlq.copy(jsMaskedPath = jsMaskedPathDLQErr) -> serializeToBytes[OutDtoWithErrors[TestDataDto]]),
        producerFactory[KafkaDto]
      )
    }
    pipeRun(listTestDataDto, flinkPipe)

    assertResult(1)(valuesTestDataDto.size)

    val kafkaDtos = topicDataArray[KafkaDto](flinkSinkProperties)
    assertResult(1)(kafkaDtos.size)
    val outDto: TestDataDto = JsonConvertInService.deserialize[TestDataDto](kafkaDtos.head.value)(TestDataDto.uaspJsonReads, serviceDataDto).right.get

    assertResult(listTestDataDto.head.copy(srt = "***MASKED***"))(outDto)
  }

}

object FlinkKafkaFunTest {

  private val testDataDto: TestDataDto = TestDataDto("st1", 12)
  private val listTestDataDto: List[TestDataDto] = List(testDataDto)

  protected val flinkSinkProperties = producerProps("topicName")
  protected val flinkSinkPropertiesDlq = producerProps("dlq_topicName")


  protected val jsMaskedPathDLQOk = Some(List(MaskedStrPathWithFunName("data.srt", "ru.vood.flink.common.mask.fun.StringMaskAll")).toJsonPath().right.get)
  protected val jsMaskedPathDLQErr = Some(List(MaskedStrPathWithFunName("data.srt", "ru.vood.flink.common.mask.fun.NumberMaskAll")).toJsonPath().right.get)
  protected val jsMaskedPathErrMainERR = Some(List(MaskedStrPathWithFunName("srt", "ru.vood.flink.common.mask.fun.NumberMaskAll")).toJsonPath().right.get)
  protected val jsMaskedPathErrMainOk = Some(List(MaskedStrPathWithFunName("srt", "ru.vood.flink.common.mask.fun.StringMaskAll")).toJsonPath().right.get)

  protected implicit val serviceDataDto = ServiceDataDto("1", "2", "3")

  protected def outDtoWithErrorsFun[IN](in: Some[IN]) = OutDtoWithErrors[IN](serviceDataDto, Some(this.getClass.getName), List("test error"), in)

  protected val dlqProcessFunctionError: DlqProcessFunction[TestDataDto, TestDataDto, OutDtoWithErrors[TestDataDto]] = new DlqProcessFunction[TestDataDto, TestDataDto, OutDtoWithErrors[TestDataDto]] {
    override def processWithDlq(dto: TestDataDto): Either[OutDtoWithErrors[TestDataDto], TestDataDto] = Left(outDtoWithErrorsFun(Some(dto)))
  }

  protected val dlqProcessFunctionErrorJs: DlqProcessFunction[JsValue, JsValue, OutDtoWithErrors[JsValue]] = new DlqProcessFunction[JsValue, JsValue, OutDtoWithErrors[JsValue]] {
    override def processWithDlq(dto: JsValue): Either[OutDtoWithErrors[JsValue], JsValue] = Left(outDtoWithErrorsFun(Some(dto)))
  }

  protected val dlqProcessFunction: DlqProcessFunction[TestDataDto, TestDataDto, OutDtoWithErrors[TestDataDto]] = new DlqProcessFunction[TestDataDto, TestDataDto, OutDtoWithErrors[TestDataDto]] {
    override def processWithDlq(dto: TestDataDto): Either[OutDtoWithErrors[TestDataDto], TestDataDto] = Right(dto)
  }


  private def producerProps(topicName: String) = {
    val properties = new Properties()
    properties.putAll(Map("bootstrap.servers" -> "bootstrap.servers").asJava)

    val kafkaPrdProperty = KafkaPrdProperty(properties)
    FlinkSinkProperties(topicName, kafkaPrdProperty, None, None, None)
  }


}



