package ru.vood.flink.common.utils.json

import play.api.libs.json.{Json, OWrites, Reads}

case class ModelVectorProperty(name: String,
                               atributeProperties: Set[AtributeProperty]
                              )

object ModelVectorProperty {
  implicit val writes: OWrites[ModelVectorProperty] = Json.writes[ModelVectorProperty]

  implicit val reads: Reads[ModelVectorProperty] = Json.reads[ModelVectorProperty]
}