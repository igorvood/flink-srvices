package ru.vood.flink.common.mask.fun

import play.api.libs.json.JsString

case class StringMaskAll() extends JsStringMaskedFun {

  override def mask(in: String): JsString = JsString("***MASKED***")

}
