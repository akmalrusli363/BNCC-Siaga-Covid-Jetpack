package com.tilikki.bnccapp.siagacovid.worldstats

data class WorldStatData (
    val countryCode: String,
    val countryName: String,
    val numOfConfirmedCase: Int,
    val numOfRecoveredCase: Int,
    val numOfDeathCase: Int,
    val numOfDailyConfirmedCase: Int,
    val numOfDailyRecoveredCase: Int,
    val numOfDailyDeathCase: Int
)