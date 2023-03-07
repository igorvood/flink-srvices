package ru.vood.flink.common.mask

import play.api.libs.json.JsValue
import ru.vood.flink.common.mask.fun.MaskedFun
import ru.vood.flink.common.mask.dto.JsMaskedPathError
import ru.vood.flink.common.mask.fun.MaskedFun

import scala.util.{Failure, Success, Try}

case class MaskedStrPathWithFunName(strPath: String, maskedFunc: String) {

  def maskedFunFactory[Q, TT <: JsValue]() = {
    val value = Try {
      val jClass = Class.forName(maskedFunc)
      val instance = jClass.getDeclaredConstructor().newInstance()
      val castedInstance = instance.asInstanceOf[MaskedFun[Q, TT]]
      castedInstance
    }
    value match {
      case Failure(exception) => Left(List(JsMaskedPathError(s"unable to load class $maskedFunc for $strPath. Cause ${exception.getMessage}")))
      case Success(value) => Right(value)
    }

  }
}
