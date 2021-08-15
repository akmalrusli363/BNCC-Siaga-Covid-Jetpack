package com.tilikki.siagacovid.model

data class VaccinationDetail(
    val group: VaccinationGroup,
    val target: Int,
    val firstDose: Int,
    val secondDose: Int,
    val firstDosePercentage: String,
    val secondDosePercentage: String,
)
