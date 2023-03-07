package ru.vood.flink.common.service

import org.apache.flink.api.scala.createTypeInformation
import ru.vood.flink.common.mask.dto.JsMaskedPathError
import ru.vood.flink.common.service.dto.{OutDtoWithErrors, ServiceDataDto}
import ru.vood.flink.common.abstraction.DlqProcessFunction
import ru.vood.flink.common.dto.UaspDto
import ru.vood.flink.common.mask.dto.JsMaskedPathError
import ru.vood.flink.common.service.dto.{OutDtoWithErrors, ServiceDataDto}

case class UaspDeserializationProcessFunction(implicit val serviceData: ServiceDataDto) extends DlqProcessFunction[Array[Byte], UaspDto, JsMaskedPathError] {

  override def processWithDlq(dto: Array[Byte]): Either[JsMaskedPathError, UaspDto] = {
    convert(dto) match {
      case Left(value) => Left(JsMaskedPathError(value.errors.mkString("\n")))
      case Right(value) => Right(value)
    }
  }

  def convert(value: Array[Byte]): Either[OutDtoWithErrors[UaspDto], UaspDto] = JsonConvertInService.deserialize[UaspDto](value)
}