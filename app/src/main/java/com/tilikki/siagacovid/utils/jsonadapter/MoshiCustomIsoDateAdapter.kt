package com.tilikki.siagacovid.utils.jsonadapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MoshiCustomIsoDateAdapter {
    companion object {
        private const val SHORT_FORMAT = "yyyy-MM-dd"
        private const val LONG_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
    }

    private val dateFormat = SimpleDateFormat(LONG_FORMAT, Locale.ENGLISH).apply {
        timeZone = TimeZone.getTimeZone("Asia/Jakarta")
    }

    @FromJson
    @Synchronized
    fun fromJson(dateString: String): Date? {
        return try {
            val trimmedDateString = dateString.split(".")[0]
            try {
                dateFormat.parse(trimmedDateString)
            } catch (e: ParseException) {
                dateFormat.applyPattern(SHORT_FORMAT)
                dateFormat.parse(trimmedDateString)
            }
        } catch (e: Exception) {
            null
        }
    }

    @ToJson
    @Synchronized
    fun toJson(date: Date): String {
        return dateFormat.format(date)
    }
}
