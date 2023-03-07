package ru.vood.flink.common.metric

import ru.vood.flink.common.service.dto.ServiceDataDto
import ru.vood.flink.common.metric.FlowDirection.FlowDirection
import ru.vood.flink.common.service.dto.ServiceDataDto

class PrometheusKafkaMetricsFunction[T](serviceData: ServiceDataDto,
                                        private val topicName: String,
                                        private val direction: FlowDirection,
                                       )
  extends PrometheusMetricsFunction[T](serviceData, s"${serviceData.serviceNameNoVersion}_${topicName}_${direction.toString.toLowerCase()}") {

  require(topicName != null, {
    "topicName is null"
  })
  require(direction != null, {
    "direction is null"
  })


}


