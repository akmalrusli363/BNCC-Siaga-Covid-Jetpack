package com.tilikki.bnccapp.siagacovid.utils

import java.util.*

object FlagUtils {
    fun printFlagEmoji(countryCode: String) : String {
        Locale("", countryCode).apply {
            return this.flagEmoji
        }
    }

    private val Locale.flagEmoji: String
        get() {
            val firstLetter = Character.codePointAt(country, 0) - 0x41 + 0x1F1E6
            val secondLetter = Character.codePointAt(country, 1) - 0x41 + 0x1F1E6
            return String(Character.toChars(firstLetter)) + String(Character.toChars(secondLetter))
        }
}