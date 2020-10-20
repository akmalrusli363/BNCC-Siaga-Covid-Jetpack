package com.tilikki.bnccapp.siagacovid.worldstats

data class WorldStatSummaryData(
    val numOfConfirmedCase: Int,
    val numOfRecoveredCase: Int,
    val numOfDeathCase: Int
) {
    fun numOfActiveCases() = numOfConfirmedCase - (numOfRecoveredCase + numOfDeathCase)
}