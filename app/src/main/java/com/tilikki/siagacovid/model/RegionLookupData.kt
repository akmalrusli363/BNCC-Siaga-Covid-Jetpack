package com.tilikki.siagacovid.model

data class RegionLookupData(
    val province: String,
    val confirmedCase: CountStatistics,
    val recoveredCase: CountStatistics,
    val deathCase: CountStatistics,
)
