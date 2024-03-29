package ru.vood.flink.common.mask.fun

import play.api.libs.json.{JsBoolean, JsNumber, JsString, JsValue}

trait MaskedFun[-IN, +T <: JsValue] {

  def mask(in: IN): T

  val name: String = this.getClass.getName

}

trait JsStringMaskedFun extends MaskedFun[String, JsString]

trait JsNumberMaskedFun extends MaskedFun[BigDecimal, JsNumber]

trait JsBooleanMaskedFun extends MaskedFun[Boolean, JsBoolean]

