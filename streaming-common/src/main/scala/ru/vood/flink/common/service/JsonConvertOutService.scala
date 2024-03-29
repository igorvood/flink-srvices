package ru.vood.flink.common.service

import play.api.libs.json.{Json, OWrites}
import ru.vood.flink.common.mask.dto.{JsMaskedPath, JsMaskedPathError}
import ru.vood.flink.common.service.dto.{KafkaDto, KafkaStrDto}
import ru.vood.flink.common.dto.Identity
import ru.vood.flink.common.mask.MaskedPredef.MaskJsValuePredef
import ru.vood.flink.common.mask.dto.{JsMaskedPath, JsMaskedPathError}
import ru.vood.flink.common.service.dto.{KafkaDto, KafkaStrDto}

object JsonConvertOutService extends Serializable {

  def serializeToBytesIdentity[T <: Identity](value: T, maskedRule: Option[JsMaskedPath])(implicit oWrites: OWrites[T]): Either[List[JsMaskedPathError], KafkaDto] = {
    serializeToStr(value, maskedRule)
      .map(d => KafkaDto(d.id.getBytes(), d.value.getBytes()))
  }

  def serializeToBytes[T](value: T, maskedRule: Option[JsMaskedPath])(implicit oWrites: OWrites[T]): Either[List[JsMaskedPathError], KafkaDto] = {
    value.serializeToBytes(maskedRule)
  }


  def serializeToStr[T <: Identity](value: T, maskedRule: Option[JsMaskedPath])(implicit oWrites: OWrites[T]): Either[List[JsMaskedPathError], KafkaStrDto] = {
    serializeToStr(value.id, value, maskedRule)
  }


  def serializeToStr[T](id: String, value: T, maskedRule: Option[JsMaskedPath])(implicit oWrites: OWrites[T]): Either[List[JsMaskedPathError], KafkaStrDto] = {
    val jsObject = Json.toJsObject(value)
    val maybeErrorsOrValue: Either[List[JsMaskedPathError], KafkaStrDto] = maskedRule
      .map { mr => {
        jsObject.toMaskedJson(mr)
          .map(jsMask => KafkaStrDto(id, Json.stringify(jsMask)))
      }
      }.getOrElse(Right(KafkaStrDto(id, Json.stringify(jsObject))))

    maybeErrorsOrValue
  }

  implicit class IdentityPredef[T <: Identity](val self: T) extends AnyVal {

    private [common] def serializeToBytes(implicit oWrites: OWrites[T]): KafkaDto = JsonConvertOutService.serializeToBytesIdentity(self, None).right.get

    def serializeToBytes(maskedRule: Option[JsMaskedPath])(implicit oWrites: OWrites[T]): Either[List[JsMaskedPathError], KafkaDto] = JsonConvertOutService.serializeToBytesIdentity(self, maskedRule)

    def serializeToStr(maskedRule: Option[JsMaskedPath])(implicit oWrites: OWrites[T]): Either[List[JsMaskedPathError], KafkaStrDto] = JsonConvertOutService.serializeToStr(self, maskedRule)

  }

  implicit class JsonPredef[T](val self: T) extends AnyVal {

    private[common] def serializeToBytes(implicit oWrites: OWrites[T]): KafkaDto = {
      serializeToBytes(None).right.get
    }

    def serializeToBytes(maskedRule: Option[JsMaskedPath])(implicit oWrites: OWrites[T]): Either[List[JsMaskedPathError], KafkaDto] =
      serializeToBytes(java.util.UUID.randomUUID().toString, maskedRule)

    def serializeToBytes(id: String, maskedRule: Option[JsMaskedPath])(implicit oWrites: OWrites[T]): Either[List[JsMaskedPathError], KafkaDto] = {
      JsonConvertOutService.serializeToStr(id, self, maskedRule).map(v => KafkaDto(
        id = v.id.getBytes(),
        value = v.value.getBytes()
      ))

    }

  }

}
