package ru.vood.flink.common.mask.fun

import play.api.libs.json.JsString

/**
 * Маскируются все символы, кроме первого, до разделяющего символа @. Почтовый домен не маскируется. Пример: a***@vtb.ru
 */
case class EMailMask() extends JsStringMaskedFun {

  override def mask(in: String): JsString = {
    val strings = in.split("@").toList

    val value = strings match {
      case Nil => throw new IllegalArgumentException("Unable to mask email @ not found")
      case head :: tl => {
        if (head.isEmpty) {
          ""
        } else {

          val inclusive = 0 until head.length - 1
          val string = inclusive.map(a => "*").mkString
          tl match {
            case Nil => head.substring(0, 1) + string
            case headT :: ttl => head.substring(0, 1) + string + "@" + tl.mkString
          }
        }
      }
    }
    JsString(value)
  }


}
