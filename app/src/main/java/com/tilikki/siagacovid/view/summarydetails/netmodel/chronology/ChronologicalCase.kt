package com.tilikki.siagacovid.view.summarydetails.netmodel.chronology

import com.squareup.moshi.Json
import com.tilikki.siagacovid.model.CaseOverview
import com.tilikki.siagacovid.model.CountStatistics
import java.util.*

data class ChronologicalCase(
    @Json(name = "key") val date: Long,
    @Json(name = "key_as_string") val dateString: String,
    @Json(name = "jumlah_positif") val dailyConfirmedCase: JsonValue,
    @Json(name = "jumlah_dirawat") val dailyActiveCase: JsonValue,
    @Json(name = "jumlah_sembuh") val dailyRecoveredCase: JsonValue,
    @Json(name = "jumlah_meninggal") val dailyDeathCase: JsonValue,
    @Json(name = "jumlah_positif_kum") val confirmedCase: JsonValue,
    @Json(name = "jumlah_dirawat_kum") val activeCase: JsonValue,
    @Json(name = "jumlah_sembuh_kum") val recoveredCase: JsonValue,
    @Json(name = "jumlah_meninggal_kum") val deathCase: JsonValue,
) {
    fun toCaseOverview(): CaseOverview {
        return CaseOverview(
            confirmedCase = CountStatistics(confirmedCase.value, dailyConfirmedCase.value),
            activeCase = CountStatistics(activeCase.value, dailyActiveCase.value),
            recoveredCase = CountStatistics(recoveredCase.value, dailyRecoveredCase.value),
            deathCase = CountStatistics(deathCase.value, dailyDeathCase.value),
            lastUpdated = Date(date)
        )
    }
}
