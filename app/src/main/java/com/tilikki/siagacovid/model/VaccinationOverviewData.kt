package com.tilikki.siagacovid.model

import java.util.*

data class VaccinationOverviewData(
    val updated: Date,
    val firstDose: CountStatistics,
    val secondDose: CountStatistics
)
