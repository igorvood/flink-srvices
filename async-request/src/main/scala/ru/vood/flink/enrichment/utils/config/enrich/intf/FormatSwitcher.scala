package ru.vood.flink.enrichment.utils.config.enrich.intf

import ru.vood.flink.enrichment.utils.config.enrich.intf.InputFormatEnum.InputFormatEnum

trait FormatSwitcher {

  val inputDataFormat: InputFormatEnum

}
