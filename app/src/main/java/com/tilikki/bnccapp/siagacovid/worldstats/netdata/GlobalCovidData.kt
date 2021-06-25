package com.tilikki.bnccapp.siagacovid.worldstats.netdata

import com.squareup.moshi.Json
import com.tilikki.bnccapp.siagacovid.model.WorldCaseOverview
import java.util.*

data class GlobalCovidData(
    @Json(name = "TotalConfirmed") val confirmedCase: Int,
    @Json(name = "TotalRecovered") val recoveredCase: Int,
    @Json(name = "TotalDeaths") val deathCase: Int,
    @Json(name = "Date") val date: Date,
) {
    fun toWorldCaseOverview(): WorldCaseOverview {
        return WorldCaseOverview(
            confirmedCase, recoveredCase, deathCase, date
        )
    }
}
