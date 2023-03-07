package ru.vood.flink.enrichment.service.dto

case class KeyedCAData(key: String,
                       newId: Option[String],
                       data: Map[String, String]
                      )
