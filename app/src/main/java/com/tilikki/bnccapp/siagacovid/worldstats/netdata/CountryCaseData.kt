package com.tilikki.bnccapp.siagacovid.worldstats.netdata

import com.squareup.moshi.Json
import com.tilikki.bnccapp.siagacovid.model.CountStatistics
import com.tilikki.bnccapp.siagacovid.model.CountryLookupData
import java.util.*

data class CountryCaseData(
    @Json(name = "Country") val countryName: String,
    @Json(name = "CountryCode") val countryCode: String,
    @Json(name = "Slug") val shortname: String,
    @Json(name = "NewConfirmed") val numOfDailyConfirmedCase: Int,
    @Json(name = "TotalConfirmed") val numOfConfirmedCase: Int,
    @Json(name = "NewRecovered") val numOfDailyRecoveredCase: Int,
    @Json(name = "TotalRecovered") val numOfRecoveredCase: Int,
    @Json(name = "NewDeaths") val numOfDailyDeathCase: Int,
    @Json(name = "TotalDeaths") val numOfDeathCase: Int,
    @Json(name = "Date") val lastUpdated: Date,
) {
    fun toCountryLookupData(): CountryLookupData {
        return CountryLookupData(
            countryCode = countryCode,
            countryName = countryName,
            shortname = shortname,
            confirmedCase = CountStatistics(numOfConfirmedCase, numOfDailyConfirmedCase),
            recoveredCase = CountStatistics(numOfRecoveredCase, numOfDailyRecoveredCase),
            deathCase = CountStatistics(numOfDeathCase, numOfDailyDeathCase),
            lastUpdated = lastUpdated
        )
    }
}
