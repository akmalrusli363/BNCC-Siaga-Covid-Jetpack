package com.tilikki.bnccapp.siagacovid.view.worldstats.netdata

import com.squareup.moshi.Json

data class GlobalCovidSummary(
    @Json(name = "Global") val global: GlobalCovidData,
    @Json(name = "Countries") val countries: List<CountryCaseData>
)
