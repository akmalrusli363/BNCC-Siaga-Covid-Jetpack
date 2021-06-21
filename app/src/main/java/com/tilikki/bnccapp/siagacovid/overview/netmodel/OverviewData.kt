package com.tilikki.bnccapp.siagacovid.overview.netmodel

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tilikki.bnccapp.siagacovid.model.CaseOverview
import java.util.*

@JsonClass(generateAdapter = true)
data class OverviewData(
    @Json(name = "penambahan") val dailyCase: DailyCase,
    @Json(name = "total") val totalCase: TotalCase,
) {
    @JsonClass(generateAdapter = true)
    data class DailyCase(
        @Json(name = "jumlah_positif") val confirmedCase: Int,
        @Json(name = "jumlah_dirawat") val activeCase: Int,
        @Json(name = "jumlah_sembuh") val recoveredCase: Int,
        @Json(name = "jumlah_meninggal") val deathCase: Int,
        @Json(name = "created") val lastUpdated: Date,
    )

    @JsonClass(generateAdapter = true)
    data class TotalCase(
        @Json(name = "jumlah_positif") val confirmedCase: Int,
        @Json(name = "jumlah_dirawat") val activeCase: Int,
        @Json(name = "jumlah_sembuh") val recoveredCase: Int,
        @Json(name = "jumlah_meninggal") val deathCase: Int,
    )

    fun toCaseOverview(): CaseOverview {
        return CaseOverview(
            totalConfirmedCase = totalCase.confirmedCase,
            totalActiveCase = totalCase.activeCase,
            totalRecoveredCase = totalCase.recoveredCase,
            totalDeathCase = totalCase.deathCase,
            dailyConfirmedCase = dailyCase.confirmedCase,
            dailyActiveCase = dailyCase.activeCase,
            dailyRecoveredCase = dailyCase.recoveredCase,
            dailyDeathCase = dailyCase.deathCase,
            lastUpdated = dailyCase.lastUpdated
        )
    }
}
