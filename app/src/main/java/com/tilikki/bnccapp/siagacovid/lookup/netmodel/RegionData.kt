package com.tilikki.bnccapp.siagacovid.lookup.netmodel

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegionData(
    @Json(name = "key") val province: String,
    @Json(name = "jumlah_kasus") val totalConfirmedCase: Int = 0,
    @Json(name = "jumlah_sembuh") val totalRecoveredCase: Int = 0,
    @Json(name = "jumlah_meninggal") val totalDeathCase: Int = 0,
    @Json(name = "penambahan") val dailyCase: DailyCase = DailyCase(),
) {
    @JsonClass(generateAdapter = true)
    data class DailyCase(
        @Json(name = "positif") val confirmedCase: Int = 0,
        @Json(name = "sembuh") val recoveredCase: Int = 0,
        @Json(name = "meninggal") val deathCase: Int = 0,
    )
}
