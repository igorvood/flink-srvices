package ru.vood.flink.common.kafka

import org.apache.flink.streaming.api.functions.sink.SinkFunction
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer
import ru.vood.flink.common.mask.dto.JsMaskedPath
import ru.vood.flink.common.utils.config.kafka.KafkaPrdProperty
import ru.vood.flink.common.abstraction.AbstractDtoMaskedSerializeService
import ru.vood.flink.common.mask.dto.{JsMaskedPath, JsMaskedPathError}
import ru.vood.flink.common.service.dto.KafkaDto
import ru.vood.flink.common.utils.config.PropertyUtil._
import ru.vood.flink.common.utils.config.kafka.KafkaPrdProperty
import ru.vood.flink.common.utils.config.{AllApplicationProperties, ConfigurationInitialise, PropertyCombiner, ReadConfigErrors}

case class FlinkSinkProperties(
                                toTopicName: String,
                                producerProps: KafkaPrdProperty,
                                producerSemantic: Option[FlinkKafkaProducer.Semantic],
                                kafkaProducerPoolSize: Option[Int] = None,
                                jsMaskedPath: Option[JsMaskedPath],
                              ) extends MetricForKafka {


  def createSinkFunction[T](factory: FlinkSinkProperties => SinkFunction[T]): SinkFunction[T] = factory(this)


}

object FlinkSinkProperties extends PropertyCombiner[FlinkSinkProperties] {

  val producerSemanticNone = FlinkKafkaProducer.Semantic.NONE

  val producerFactoryDefault: FlinkSinkProperties => SinkFunction[KafkaDto] = { propsModel =>

    KafkaProducerService.getKafkaProducer[KafkaDto](
      topicName = propsModel.toTopicName,
      kafkaProps = propsModel.producerProps.property,
      serializer = new FlinkKafkaSerializationSchema(propsModel.toTopicName),
      producerSemantic = propsModel.producerSemantic.getOrElse(FlinkKafkaProducer.Semantic.NONE),
      kafkaProducerPoolSize = propsModel.kafkaProducerPoolSize.getOrElse(5)
    )
  }

  protected override def createMayBeErr[CONFIGURATION](prf: String)(implicit appProps: AllApplicationProperties, configurationInitialise: ConfigurationInitialise[CONFIGURATION]): Either[ReadConfigErrors, FlinkSinkProperties] =
    for {
      appTopicName <- propertyVal[String](prf, "toTopic")(appProps, configurationInitialise, s)
      producerProps <- KafkaPrdProperty.create(s"$prf.toTopic.prd")
      producerSemantic <- propertyValOptional[FlinkKafkaProducer.Semantic](prf, "producerSemantic")(appProps, configurationInitialise, { v => FlinkKafkaProducer.Semantic.valueOf(v) })
      kafkaProducerPoolSize <- propertyValOptional[Int](prf, "kafkaProducerPoolSize")
      jsMaskedPathOption <- createByClassOption(prf, JsMaskedPath.getClass, { p =>
        JsMaskedPath.create(p)
      })
    } yield new FlinkSinkProperties(
      toTopicName = appTopicName,
      producerProps = producerProps,
      producerSemantic = producerSemantic,
      kafkaProducerPoolSize = kafkaProducerPoolSize,
      jsMaskedPath = jsMaskedPathOption,
    )
}
