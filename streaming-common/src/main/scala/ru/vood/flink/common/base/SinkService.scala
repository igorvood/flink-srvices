package ru.vood.flink.common.base

import org.apache.flink.streaming.api.datastream.DataStreamSink
import org.apache.flink.streaming.api.scala.DataStream

trait SinkService[IN, OUT] {
  def sink(stream: DataStream[IN]): DataStreamSink[OUT]
}
