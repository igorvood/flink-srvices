package ru.vood.flink.common.mask.fun

import play.api.libs.json.{JsBoolean, JsFalse}

case class BooleanMaskAll() extends JsBooleanMaskedFun {

  override def mask(in: Boolean): JsBoolean = JsFalse
}
