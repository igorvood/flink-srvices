package ru.vood.flink.common.abstraction

import ru.vood.flink.common.service.dto.KafkaDto
import ru.vood.flink.common.mask.dto.{JsMaskedPath, JsMaskedPathError}
import ru.vood.flink.common.service.JsonConvertOutService.JsonPredef
import ru.vood.flink.common.service.dto.{KafkaDto, OutDtoWithErrors}

class TestDataDtoMaskedSerializeServiceDLQ(jsMaskedPath: Option[JsMaskedPath]) extends AbstractOutDtoWithErrorsMaskedSerializeService[TestDataDto](jsMaskedPath) {
  override def convert(value: OutDtoWithErrors[TestDataDto], jsMaskedPath: Option[JsMaskedPath]): Either[List[JsMaskedPathError], KafkaDto] = {
    value.serializeToBytes(jsMaskedPath)
  }
}

class TestDataDtoMaskedSerializeServiceMain(jsMaskedPath: Option[JsMaskedPath]) extends AbstractDtoMaskedSerializeService[TestDataDto](jsMaskedPath) {
  override def convert(value: TestDataDto, jsMaskedPath: Option[JsMaskedPath]): Either[List[JsMaskedPathError], KafkaDto] = value.serializeToBytes(jsMaskedPath)
}

