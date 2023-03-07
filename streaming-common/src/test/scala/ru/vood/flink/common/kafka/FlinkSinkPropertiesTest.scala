package ru.vood.flink.common.kafka

import org.scalatest.flatspec.AnyFlatSpec
import ru.vood.flink.common.mask.dto.{JsMaskedPathObject, JsStringMaskedPathValue}
import ru.vood.flink.common.mask.fun.{PhoneStrMaskService, StringMaskAll}
import ru.vood.flink.common.utils.config.AllApplicationProperties
import ru.vood.flink.common.utils.config.kafka.KafkaPrdProperty

import java.util.Properties
import scala.collection.JavaConverters.mapAsJavaMapConverter
import scala.collection.mutable

class FlinkSinkPropertiesTest extends AnyFlatSpec {

  "FlinkSinkProperties reads " should " OK" in {

    val PRD = "prefix.FlinkSinkProperties$.toTopic.prd."
    val map = Map(
      "prefix.FlinkSinkProperties$.toTopic" -> "asd",
      PRD + "bootstrap.servers" -> "asd",

      "prefix.FlinkSinkProperties$.JsMaskedPath$.f1" -> "ru.vood.flink.common.mask.fun.StringMaskAll",
      "prefix.FlinkSinkProperties$.JsMaskedPath$.f2" -> "ru.vood.flink.common.mask.fun.PhoneStrMaskService",
    )
    val props = SomeAppProps.defaultConfiguration("prefix")(AllApplicationProperties(map), mutable.Set[String]())

    val stringToString = map
      .filter(p => p._1.contains(PRD))
      .map(p => p._1.replace(PRD, "") -> p._2)

    val properties = new Properties()
    properties.putAll(stringToString.asJava)

    val pathObject = JsMaskedPathObject(Map(
      "f1" -> JsStringMaskedPathValue(StringMaskAll()),
      "f2" -> JsStringMaskedPathValue(PhoneStrMaskService()),
    ))

    assertResult(
      expected = SomeAppProps(
        flinkSinkProperties = FlinkSinkProperties(
          toTopicName = "asd",
          producerProps = KafkaPrdProperty(
            property = properties
          ),
          producerSemantic = None,
          kafkaProducerPoolSize = None,
          jsMaskedPath = Some(pathObject),
        )
      )
    )(props)


  }

}
