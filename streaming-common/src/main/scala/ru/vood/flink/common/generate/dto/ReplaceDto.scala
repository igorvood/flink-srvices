package ru.vood.flink.common.generate.dto

case class ReplaceDto(value: String,
                      placeholder: Option[PlaceholderDto] = None)
