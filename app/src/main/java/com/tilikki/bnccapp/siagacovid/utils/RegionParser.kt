package com.tilikki.bnccapp.siagacovid.utils

import java.util.*

object RegionParser {
    fun capitalizeRegionName(region: String): String {
        val undercased = region.toLowerCase(Locale.ROOT)
        val capitalizedWords = undercased.capitalizeWords()
        return capitalizedWords.capitalizeSpecialDistrictNames().capitalizeAbbreviation()
    }

    private fun String.capitalizeWords(): String {
        return split(" ").joinToString(" ") {
            it.capitalize(Locale.ROOT)
        }
    }

    private fun String.capitalizeAbbreviation(): String {
        return split(".").joinToString(".") {
            it.capitalize(Locale.ROOT)
        }
    }

    private fun String.capitalizeSpecialDistrictNames(): String {
        return replaceFirst("DKI ", "DKI ", true)
            .replaceFirst("DI ", "DI ")
    }
}