package com.tilikki.bnccapp.siagacovid.utils.jsonadapter

import android.util.Log
import com.squareup.moshi.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MoshiDateAdapter : JsonAdapter<Date>() {
    companion object {
        private const val SHORT_FORMAT = "yyyy-MM-dd"
        private const val LONG_FORMAT = "yyyy-MM-dd hh:mm:ss"
    }

    private val dateFormat = SimpleDateFormat(LONG_FORMAT, Locale.ENGLISH).apply {
       timeZone = TimeZone.getTimeZone("Asia/Jakarta")
    }

    @FromJson
    override fun fromJson(reader: JsonReader): Date? {
        return try {
            val dateAsString = reader.nextString()
            synchronized(dateFormat) {
                try {
                    dateFormat.parse(dateAsString)
                } catch (e: ParseException) {
                    dateFormat.applyPattern(SHORT_FORMAT)
                    dateFormat.parse(dateAsString)
                }
            }
        } catch (e: Exception) {
            Log.e("ele", e.toString(), e)
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
