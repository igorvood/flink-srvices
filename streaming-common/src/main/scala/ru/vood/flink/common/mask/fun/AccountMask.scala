package ru.vood.flink.common.mask.fun

import play.api.libs.json.JsString

/**
 * Маскируются 6 символов начиная с пятого с права, пример: 123456778899******1234
 */
case class AccountMask() extends JsStringMaskedFun {

  override def mask(in: String): JsString = {
    val maskedDigit = "******"
    JsString(in.substring(0, 10) + maskedDigit + in.substring(16))
  }

}
