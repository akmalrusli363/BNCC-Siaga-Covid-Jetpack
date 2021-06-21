package com.tilikki.bnccapp.siagacovid.utils.jsonadapter

import com.squareup.moshi.*
import java.text.SimpleDateFormat
import java.util.*

class MoshiDateAdapter : JsonAdapter<Date>() {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH).apply {
       timeZone = TimeZone.getTimeZone("Asia/Jakarta")
    }

    @FromJson
    override fun fromJson(reader: JsonReader): Date? {
        return try {
            val dateAsString = reader.nextString()
            synchronized(dateFormat) {
                dateFormat.parse(dateAsString)
            }
        } catch (e: Exception) {
            null
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: Date?) {
        if (value != null) {
            synchronized(dateFormat) {
                writer.value(value.toString())
            }
        }
    }
}
