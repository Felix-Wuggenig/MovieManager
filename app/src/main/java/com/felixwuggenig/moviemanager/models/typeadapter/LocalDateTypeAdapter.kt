package com.felixwuggenig.moviemanager.models.typeadapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class LocalDateTypeAdapter : TypeAdapter<LocalDate>() {

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())

    override fun write(out: JsonWriter?, value: LocalDate?) {
        out?.value(formatter.format(value))
    }

    override fun read(reader: JsonReader?): LocalDate {
        var date = LocalDate.now()
        if (reader != null) {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull()
                return date
            }
            val dateString = reader.nextString()
            date = LocalDate.from(formatter.parse(dateString))
        }
        return date
    }
}