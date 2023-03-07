package ru.vood.flink.common.generate.dto

case class PlaceholderDto(replaceValue: String,
                          calcNewValue: Option[String] = None,
                          upToHead: Boolean = false,

                         )
