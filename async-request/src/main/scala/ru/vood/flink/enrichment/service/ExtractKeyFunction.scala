package ru.vood.flink.enrichment.service

import org.apache.flink.api.scala.createTypeInformation
import play.api.libs.json.OFormat.oFormatFromReadsAndOWrites
import play.api.libs.json.{JsError, JsSuccess, JsValue}
import ru.vood.flink.common.abstraction.DlqProcessFunction
import ru.vood.flink.common.dto.UaspDto
import ru.vood.flink.common.service.dto.{OutDtoWithErrors, ServiceDataDto}
import ru.vood.flink.enrichment.dao.JsonPredef.PreDef
import ru.vood.flink.enrichment.service.dto.KeyedCAData
import ru.vood.flink.enrichment.utils.config.enrich.intf.InputFormatEnum._
import ru.vood.flink.enrichment.utils.config.enrich.intf.{EnrichProperty, EnrichPropertyFields, EnrichPropertyWithDlq, FormatSwitcher}


class ExtractKeyFunction(val serviceDataDto: ServiceDataDto,
                         val enrichProperty: EnrichProperty with EnrichPropertyWithDlq with EnrichPropertyFields with FormatSwitcher
                        ) extends DlqProcessFunction[JsValue, KeyedCAData, OutDtoWithErrors[JsValue]] {

  override def processWithDlq(dto: JsValue): Either[OutDtoWithErrors[JsValue], KeyedCAData] = {

    val mayBeValidDataState = enrichProperty.inputDataFormat match {
      case FlatJsonFormat => enrichProperty.validateFieldsAndExtractData(dto)
      case UaspDtoFormat => dto.validate[UaspDto] match {
        case JsSuccess(uaspDto, _) => enrichProperty.validateFieldsAndExtractData(uaspDto)
        case JsError(errors) => Left(errors.extractStringError())
      }
    }

    mayBeValidDataState match {
      case Right(value) => Right(value)
      case Left(value) => Left(OutDtoWithErrors[JsValue](serviceDataDto, Some(this.getClass.getName), List(value), Some(dto)))
    }

  }
}
