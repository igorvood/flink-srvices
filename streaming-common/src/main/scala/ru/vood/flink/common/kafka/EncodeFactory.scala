package ru.vood.flink.common.kafka

import com.sksamuel.avro4s.{AvroSchema, Encoder, ScalePrecision}
import org.apache.avro.generic.{GenericDatumWriter, GenericRecord}
import ru.vood.flink.common.constants.BigDecimalConst.{PRECISION, SCALE}
import ru.vood.flink.common.dto.UaspDto


object EncodeFactory {
  def getEncode: (Encoder[UaspDto], GenericDatumWriter[GenericRecord]) = {
    implicit val sp: ScalePrecision = ScalePrecision(SCALE, PRECISION)

    val schema = AvroSchema[UaspDto]
    val encoder = Encoder[UaspDto]
    val writer = new GenericDatumWriter[GenericRecord](schema)
    (encoder, writer)
  }

}
