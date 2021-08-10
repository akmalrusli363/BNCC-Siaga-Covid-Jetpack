package com.tilikki.siagacovid.view.summarydetails.netmodel.chronology

import com.squareup.moshi.Json

data class JsonValue(
    @Json(name = "value") val value: Int
)
