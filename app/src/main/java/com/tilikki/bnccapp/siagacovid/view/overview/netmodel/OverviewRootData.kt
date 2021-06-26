package com.tilikki.bnccapp.siagacovid.view.overview.netmodel

import com.squareup.moshi.Json

data class OverviewRootData(
    @Json(name = "update") val update: OverviewData
)
