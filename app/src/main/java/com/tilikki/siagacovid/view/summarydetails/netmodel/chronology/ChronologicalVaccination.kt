package com.tilikki.siagacovid.view.summarydetails.netmodel.chronology

import com.squareup.moshi.Json
import com.tilikki.siagacovid.model.CountStatistics
import com.tilikki.siagacovid.model.VaccinationOverviewData
import java.util.*

data class ChronologicalVaccination(
    @Json(name = "key") val date: Long,
    @Json(name = "key_as_string") val dateString: String,
    @Json(name = "jumlah_vaksinasi_1") val firstDose: JsonValue,
    @Json(name = "jumlah_vaksinasi_2") val secondDose: JsonValue,
    @Json(name = "jumlah_jumlah_vaksinasi_1_kum") val cumulativeFirstDose: JsonValue,
    @Json(name = "jumlah_jumlah_vaksinasi_2_kum") val cumulativeSecondDose: JsonValue,
) {
    fun toVaccinationOverviewData(): VaccinationOverviewData {
        return VaccinationOverviewData(
            updated = Date(date),
            firstDose = mapToCountStatistics(cumulativeFirstDose, firstDose),
            secondDose = mapToCountStatistics(cumulativeSecondDose, secondDose)
        )
    }

    private fun mapToCountStatistics(total: JsonValue, daily: JsonValue): CountStatistics {
        return CountStatistics(total = total.value, added = daily.value)
    }
}
