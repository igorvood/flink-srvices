package ru.vood.flink.enrichment.service

import org.apache.flink.streaming.api.scala.createTypeInformation
import play.api.libs.json.JsValue
import ru.vood.flink.common.abstraction.DlqProcessFunction
import ru.vood.flink.common.mask.dto.JsMaskedPathError
import ru.vood.flink.common.service.JsonConvertInService
import ru.vood.flink.common.service.dto.ServiceDataDto

class JsValueConsumer(val serviceDataDto: ServiceDataDto) extends DlqProcessFunction[Array[Byte], JsValue, JsMaskedPathError] {

  override def processWithDlq(dto: Array[Byte]): Either[JsMaskedPathError, JsValue] = {
    val value = JsonConvertInService.extractJsValue[JsMaskedPathError](dto)(serviceDataDto)
    val dtoOrValue = value match {
      case Right(v) => Right(v)
      case Left(value) => Left(JsMaskedPathError(value.errors.mkString("\n")))
    }
    dtoOrValue
  }


}
