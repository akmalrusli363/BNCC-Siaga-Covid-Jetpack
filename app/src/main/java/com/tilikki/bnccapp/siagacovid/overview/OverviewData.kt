package com.tilikki.bnccapp.siagacovid.overview

data class OverviewData (
    val totalConfirmedCase: Int = 0,
    val totalActiveCase: Int = 0,
    val totalRecoveredCase: Int = 0,
    val totalDeathCase: Int = 0,
    val dailyConfirmedCase: Int = 0,
    val dailyActiveCase: Int = 0,
    val dailyRecoveredCase: Int = 0,
    val dailyDeathCase: Int = 0
)