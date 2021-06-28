package com.tilikki.bnccapp.siagacovid.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object StringParser {
    fun parseInt(str: String): Int {
        return str.replace(",", "").toInt()
    }

    fun parseShortDate(str: String): Date {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        dateFormat.timeZone = TimeZone.getTimeZone("Asia/Jakarta")

        return dateFormat.parse(str)!!
    }

    fun parseDate(str: String): Date {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH)
        dateFormat.timeZone = TimeZone.getTimeZone("Asia/Jakarta")

        return dateFormat.parse(str)!!
    }

    fun parseDomain(url: String): String {
        val regex = "^(\\w+://)?([\\w.:@]+)[/|?](.*)$"
        return url.replace(Regex(regex), "$2")
    }

    fun formatDate(date: Date): String {
        val dateFormat: DateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, Locale.UK)
        return dateFormat.format(date)
    }

    fun formatShortDate(date: Date): String {
        val dateFormat: DateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.UK)
        return dateFormat.format(date)
    }
}
