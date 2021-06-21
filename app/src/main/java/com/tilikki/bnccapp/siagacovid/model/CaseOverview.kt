package com.tilikki.bnccapp.siagacovid.model

import java.util.*

data class CaseOverview(
    val totalConfirmedCase: Int,
    val totalActiveCase: Int,
    val totalRecoveredCase: Int,
    val totalDeathCase: Int,
    val dailyConfirmedCase: Int,
    val dailyActiveCase: Int,
    val dailyRecoveredCase: Int,
    val dailyDeathCase: Int,
    val lastUpdated: Date,
)
