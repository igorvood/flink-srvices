package ru.vood.flink.common.abstraction

import ru.vood.flink.common.mask.dto.{JsMaskedPath, JsMaskedPathError}
import ru.vood.flink.common.service.dto.{KafkaDto, OutDtoWithErrors}
import ru.vood.flink.common.mask.dto.{JsMaskedPath, JsMaskedPathError}
import ru.vood.flink.common.service.dto.{KafkaDto, OutDtoWithErrors}

abstract class AbstractMaskedSerializeService[IN, OUT](val jsMaskedPath: Option[JsMaskedPath]) extends Serializable /*extends RichMapFunction[IN, Either[List[JsMaskedPathError], OUT]]*/ {

  def convert(value: IN, jsMaskedPath: Option[JsMaskedPath]): Either[List[JsMaskedPathError], OUT]

}

abstract class AbstractDtoMaskedSerializeService[IN](jsMaskedPath: Option[JsMaskedPath]) extends AbstractMaskedSerializeService[IN, KafkaDto](jsMaskedPath)

abstract class AbstractOutDtoWithErrorsMaskedSerializeService[IN](jsMaskedPath: Option[JsMaskedPath]) extends AbstractDtoMaskedSerializeService[OutDtoWithErrors[IN]](jsMaskedPath)

