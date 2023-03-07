package ru.vood.flink.enrichment.service

import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.co.KeyedCoProcessFunction
import org.apache.flink.util.Collector
import ru.vood.flink.common.dto.UaspDto
import ru.vood.flink.common.service.dto.{OutDtoWithErrors, ServiceDataDto}
import ru.vood.flink.enrichment.service.dto.{KeyedCAData, KeyedUasp}
import ru.vood.flink.enrichment.utils.config.enrich.CommonEnrichProperty

class KeyedEnrichCommonCoProcessService(val serviceDataDto: ServiceDataDto,
                                        val commonEnrichProperty: CommonEnrichProperty
                                       ) extends KeyedCoProcessFunction[String, KeyedUasp, KeyedCAData, Either[OutDtoWithErrors[UaspDto], UaspDto]] {

  val valueStateDescriptor = new ValueStateDescriptor(
    s"CommonState",
    TypeInformation.of(classOf[Map[String, String]])
  )
  private var dataState: ValueState[Map[String, String]] = _

  override def processElement1(value: KeyedUasp, ctx: KeyedCoProcessFunction[String, KeyedUasp, KeyedCAData, Either[OutDtoWithErrors[UaspDto], UaspDto]]#Context, out: Collector[Either[OutDtoWithErrors[UaspDto], UaspDto]]): Unit = {
    val mapState = Option(dataState.value()).getOrElse(Map.empty)
    val fieldsList = commonEnrichProperty.fields
    val mayBeError = value.enrichMainStream(fieldsList) { fieldKey => mapState.get(fieldKey) }

    val tupleOrUasp = mayBeError match {
      case Right(ok) => Right(ok.uaspDto)
      case Left(err) =>
        Left(OutDtoWithErrors[UaspDto](serviceDataDto, Some(this.getClass.getName), List(err), Some(value.uaspDto)))
    }
    out.collect(tupleOrUasp)

  }

  override def processElement2(value: KeyedCAData, ctx: KeyedCoProcessFunction[String, KeyedUasp, KeyedCAData, Either[OutDtoWithErrors[UaspDto], UaspDto]]#Context, out: Collector[Either[OutDtoWithErrors[UaspDto], UaspDto]]): Unit = {
    dataState.update(value.data)
  }

  override def open(config: Configuration): Unit = {
    dataState = getRuntimeContext.getState(valueStateDescriptor)
  }
}
