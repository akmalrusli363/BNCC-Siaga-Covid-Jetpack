package com.tilikki.siagacovid.view.summarydetails.netmodel

import com.squareup.moshi.Json
import com.tilikki.siagacovid.model.CountStatistics
import com.tilikki.siagacovid.model.VaccinationOverviewData
import com.tilikki.siagacovid.view.summarydetails.netmodel.chronology.ChronologicalVaccination
import java.util.*

data class CovidVaccinationData(
    @Json(name = "penambahan") val daily: DailyVaccination,
    @Json(name = "harian") val history: List<ChronologicalVaccination>,
    @Json(name = "total") val total: OverallVaccination

) {
    data class DailyVaccination(
        @Json(name = "created") val date: Date,
        @Json(name = "jumlah_vaksinasi_1") val firstDose: Int,
        @Json(name = "jumlah_vaksinasi_2") val secondDose: Int,
    )

    data class OverallVaccination(
        @Json(name = "jumlah_vaksinasi_1") val firstDose: Int,
        @Json(name = "jumlah_vaksinasi_2") val secondDose: Int,
    )

    fun toVaccinationOverviewData(): VaccinationOverviewData {
        return VaccinationOverviewData(
            updated = daily.date,
            firstDose = CountStatistics(added = daily.firstDose, total = total.firstDose),
            secondDose = CountStatistics(added = daily.secondDose, total = total.secondDose)
        )
    }
}
