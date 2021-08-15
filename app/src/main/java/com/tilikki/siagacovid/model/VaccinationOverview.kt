package com.tilikki.siagacovid.model

import java.util.*

data class VaccinationOverview(
    val date: Date,
    val targetVaccination: Int,
    val firstDose: Int,
    val secondDose: Int,
    val firstDosePercentage: String,
    val secondDosePercentage: String,
    val vaccinationStages: Map<VaccinationGroup, VaccinationDetail>
)
