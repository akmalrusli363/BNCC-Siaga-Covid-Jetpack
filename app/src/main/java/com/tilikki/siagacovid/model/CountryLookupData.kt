package com.tilikki.siagacovid.model

import java.util.*

data class CountryLookupData(
    val countryCode: String,
    val countryName: String,
    val shortname: String,
    val confirmedCase: CountStatistics,
    val recoveredCase: CountStatistics,
    val deathCase: CountStatistics,
    val lastUpdated: Date
) {
    private fun numOfActiveCase(): Int {
        return confirmedCase.total - (recoveredCase.total + deathCase.total)
    }

    fun positivityRate(): Double = numOfActiveCase() / confirmedCase.total.toDouble()
    fun recoveryRate(): Double = recoveredCase.total / confirmedCase.total.toDouble()
    fun deathRate(): Double = deathCase.total / confirmedCase.total.toDouble()
}
