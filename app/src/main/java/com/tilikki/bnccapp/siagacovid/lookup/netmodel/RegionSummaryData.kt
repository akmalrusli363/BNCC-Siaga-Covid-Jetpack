package com.tilikki.bnccapp.siagacovid.lookup.netmodel

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class RegionSummaryData(
    @Json(name = "last_date") val lastUpdated: Date,
    @Json(name = "list_data") val regionData: List<RegionData>,
)
