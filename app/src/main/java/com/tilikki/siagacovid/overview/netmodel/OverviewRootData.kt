package com.tilikki.siagacovid.overview.netmodel

import com.squareup.moshi.Json

data class OverviewRootData(
    @Json(name = "update") val update: OverviewData
)
