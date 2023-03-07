package ru.vood.flink.common.kafka

import org.apache.flink.api.common.serialization.{AbstractDeserializationSchema, DeserializationSchema}
import org.apache.flink.streaming.connectors.kafka.{FlinkKafkaConsumer, KafkaDeserializationSchema}
import ru.vood.flink.common.utils.config.kafka.KafkaCnsProperty
import ru.vood.flink.common.utils.config.{AllApplicationProperties, ConfigurationInitialise, PropertyCombiner, ReadConfigErrors}
import ru.vood.flink.common.kafka.FlinkConsumerProperties.deserializationSchema
import ru.vood.flink.common.utils.config.PropertyUtil.propertyVal
import ru.vood.flink.common.utils.config.kafka.KafkaCnsProperty
import ru.vood.flink.common.utils.config.{AllApplicationProperties, ConfigurationInitialise, PropertyCombiner, ReadConfigErrors}

case class FlinkConsumerProperties(fromTopic: String,
                                   kafkaCnsProperty: KafkaCnsProperty,
                                  ) extends MetricForKafka {

  def createConsumer(): FlinkKafkaConsumer[Array[Byte]] = ConsumerFactory.getKafkaConsumer(
    fromTopic, deserializationSchema, kafkaCnsProperty.property)

  def createConsumer[T](deserializationSchema: DeserializationSchema[T]): FlinkKafkaConsumer[T] =
    ConsumerFactory.getKafkaConsumer(
      topic = fromTopic,
      des = deserializationSchema,
      properties = kafkaCnsProperty.property)

  def createConsumer[T](deserializationSchema: KafkaDeserializationSchema[T]): FlinkKafkaConsumer[T] =
    ConsumerFactory.getKafkaConsumer(
      topic = fromTopic,
      deserializationSchema,
      properties = kafkaCnsProperty.property)

}

object FlinkConsumerProperties extends PropertyCombiner[FlinkConsumerProperties] {
  val deserializationSchema: DeserializationSchema[Array[Byte]] = new AbstractDeserializationSchema[Array[Byte]]() {
    override def deserialize(bytes: Array[Byte]): Array[Byte] = bytes
  }

  protected override def createMayBeErr[CONFIGURATION](prf: String)(implicit appProps: AllApplicationProperties, configurationInitialise: ConfigurationInitialise[CONFIGURATION]): Either[ReadConfigErrors, FlinkConsumerProperties] =
    for {
      appTopicName <- propertyVal[String](prf, "fromTopic")
      kafkaCnsProperty <- KafkaCnsProperty.create(prf + ".fromTopic.cns")

    } yield new FlinkConsumerProperties(appTopicName, kafkaCnsProperty)
}
