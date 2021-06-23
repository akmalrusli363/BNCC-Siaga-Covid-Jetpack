package com.tilikki.bnccapp.siagacovid.model

data class RegionLookupData(
    val province: String,
    val confirmedCase: CountStatistics,
    val recoveredCase: CountStatistics,
    val deathCase: CountStatistics,
)
