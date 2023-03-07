package ru.vood.flink.common.tests

import org.scalatest.Suites
import ru.vood.flink.common.service.JsonConverterTest

class AggregateTestSuites extends Suites {
  (new JsonConverterTest).execute()

}
