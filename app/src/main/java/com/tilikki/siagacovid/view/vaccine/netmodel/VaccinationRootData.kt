package com.tilikki.siagacovid.view.vaccine.netmodel

import com.squareup.moshi.Json
import java.util.*

data class VaccinationRootData(
    @Json(name = "last_updated") val lastUpdated: Date,
    @Json(name = "monitoring") val monitoring: List<VaccinationData>
)
