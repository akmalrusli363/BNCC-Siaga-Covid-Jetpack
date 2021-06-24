package com.tilikki.bnccapp.siagacovid.lookup.netmodel

import com.squareup.moshi.Json
import java.util.*

data class RegionSummaryData(
    @Json(name = "last_date") val lastUpdated: Date,
    @Json(name = "list_data") val regionData: List<RegionData>,
)
