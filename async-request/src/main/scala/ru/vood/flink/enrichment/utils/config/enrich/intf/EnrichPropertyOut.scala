package ru.vood.flink.enrichment.utils.config.enrich.intf

import ru.vood.flink.common.kafka.FlinkSinkProperties

trait EnrichPropertyOut {

  /*топик куда записываются успешно обработанные сообщения*/
  val toTopicProp: FlinkSinkProperties

  require(toTopicProp != null, "toTopicName must be not null")
}
