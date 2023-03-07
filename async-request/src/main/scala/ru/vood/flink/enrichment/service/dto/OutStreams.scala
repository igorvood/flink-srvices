package ru.vood.flink.enrichment.service.dto

import org.apache.flink.streaming.api.scala.DataStream
import ru.vood.flink.common.dto.UaspDto

case class OutStreams(mainStream: DataStream[UaspDto])
