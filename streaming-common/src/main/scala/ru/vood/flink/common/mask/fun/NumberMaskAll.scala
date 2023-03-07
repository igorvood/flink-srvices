package ru.vood.flink.common.mask.fun

import play.api.libs.json.JsNumber

case class NumberMaskAll() extends JsNumberMaskedFun {

  override def mask(in: BigDecimal): JsNumber = JsNumber(0)

}
