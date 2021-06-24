package com.tilikki.bnccapp.siagacovid.overview.netmodel

import com.squareup.moshi.Json
import com.tilikki.bnccapp.siagacovid.model.CaseOverview
import java.util.*

data class OverviewData(
    @Json(name = "penambahan") val dailyCase: DailyCase,
    @Json(name = "total") val totalCase: TotalCase,
) {
    data class DailyCase(
        @Json(name = "jumlah_positif") val confirmedCase: Int,
        @Json(name = "jumlah_dirawat") val activeCase: Int,
        @Json(name = "jumlah_sembuh") val recoveredCase: Int,
        @Json(name = "jumlah_meninggal") val deathCase: Int,
        @Json(name = "created") val lastUpdated: Date,
    )

    data class TotalCase(
        @Json(name = "jumlah_positif") val confirmedCase: Int,
        @Json(name = "jumlah_dirawat") val activeCase: Int,
        @Json(name = "jumlah_sembuh") val recoveredCase: Int,
        @Json(name = "jumlah_meninggal") val deathCase: Int,
    )

    fun toCaseOverview(): CaseOverview {
        return CaseOverview(totalCase, dailyCase)
    }
}
