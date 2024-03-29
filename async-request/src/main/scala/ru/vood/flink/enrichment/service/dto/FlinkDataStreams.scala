package ru.vood.flink.enrichment.service.dto

import org.apache.flink.streaming.api.scala.DataStream
import play.api.libs.json.JsValue
import ru.vood.flink.common.dto.UaspDto

case class FlinkDataStreams( // поток Way4 без глобального идентификатора
                             mainDataStream: DataStream[UaspDto],
                             // единый пток со всеми сообщениями с информацией по обогащению, сюда сливается информация с произвольного количества очередей
                             commonStream: Option[DataStream[JsValue]],
                             globalIdStream: Option[DataStream[JsValue]],
                           ) {

}
