package com.tilikki.bnccapp.siagacovid.worldstats

data class WorldStatLookupData(
    val countryCode: String,
    val countryName: String,
    val numOfConfirmedCase: Int,
    val numOfRecoveredCase: Int,
    val numOfDeathCase: Int,
    val numOfDailyConfirmedCase: Int,
    val numOfDailyRecoveredCase: Int,
    val numOfDailyDeathCase: Int,
) {
    private fun numOfActiveCase(): Int = numOfConfirmedCase - numOfRecoveredCase
    fun positivityRate(): Double = numOfActiveCase() / numOfRecoveredCase.toDouble()
    fun recoveryRate(): Double = numOfRecoveredCase / numOfRecoveredCase.toDouble()
    fun deathRate(): Double = numOfDeathCase / numOfRecoveredCase.toDouble()
}