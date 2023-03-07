package ru.vood.flink.enrichment.utils.config.enrich.intf

import ru.vood.flink.common.kafka.FlinkConsumerProperties

trait EnrichProperty {
  /*топик откуда вычитывается информация*/
  val fromTopic: FlinkConsumerProperties

}
