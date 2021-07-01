package com.tilikki.siagacovid.model

import java.util.*

data class WorldCaseOverview(
    val confirmedCase: Int,
    val recoveredCase: Int,
    val deathCase: Int,
    val lastUpdated: Date
) {
    fun activeCases() = confirmedCase - (recoveredCase + deathCase)
}
