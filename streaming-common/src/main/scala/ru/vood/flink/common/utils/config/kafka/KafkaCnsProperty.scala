package ru.vood.flink.common.utils.config.kafka

import ru.vood.flink.common.utils.config.PropertyUtil.asProperty
import ru.vood.flink.common.utils.config.{AllApplicationProperties, ConfigurationInitialise, PropertyCombiner, ReadConfigErrors}

import java.util.Properties


case class KafkaCnsProperty(property: Properties) extends MandatoryPropertyChecker {

  require(nullProperties(property) == "", s"Properties ${nullProperties(property)} must be not null")

  override def requiredProperty: Set[String] =
    Set(
      "bootstrap.servers",
      //      "group.id"
    )

}


object KafkaCnsProperty extends PropertyCombiner[KafkaCnsProperty] {

  protected override def createMayBeErr[CONFIGURATION](prf: String)(implicit appProps: AllApplicationProperties, configurationInitialise: ConfigurationInitialise[CONFIGURATION]): Either[ReadConfigErrors, KafkaCnsProperty] =
    for {
      tn <- asProperty(prf)
    } yield KafkaCnsProperty(tn)
}
