package ru.vood.flink.enrichment.utils.config.enrich.intf

import ru.vood.flink.common.kafka.FlinkSinkProperties

trait EnrichPropertyWithDlq {
  /*топик с обратным потоком, содержащий сообщение Way4 уже обогащенное глобальным идентификатором*/
  val dlqTopicProp: Option[FlinkSinkProperties]

  require(dlqTopicProp != null, "dlqTopicName must be not null")

}
