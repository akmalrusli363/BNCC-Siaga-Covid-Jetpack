package com.tilikki.siagacovid.view.summarydetails.netmodel

import com.squareup.moshi.Json

data class TestingVaccinationRootData(
    @Json(name = "pemeriksaan") val testing: CovidTestingData,
    @Json(name = "vaksinasi") val vaccination: CovidVaccinationData
)
