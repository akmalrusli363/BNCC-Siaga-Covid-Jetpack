package com.tilikki.bnccapp.siagacovid.overview.netmodel

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OverviewRootData(
    @Json(name = "update") val update: OverviewData
)
