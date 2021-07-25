package com.tilikki.siagacovid.view.summarydetails.netmodel

import com.squareup.moshi.Json
import com.tilikki.siagacovid.model.CountStatistics
import com.tilikki.siagacovid.model.CovidTestingData
import java.util.*

data class CovidTestingData(
    @Json(name = "penambahan") val daily: DailyTesting,
    @Json(name = "total") val total: OverallTesting
) {
    data class DailyTesting(
        @Json(name = "created") val date: Date,
        @Json(name = "jumlah_spesimen_antigen") val antigenSamples: Int,
        @Json(name = "jumlah_orang_antigen") val antigenTested: Int,
        @Json(name = "jumlah_spesimen_pcr_tcm") val pcrSamples: Int,
        @Json(name = "jumlah_orang_pcr_tcm") val pcrTested: Int,
    )

    data class OverallTesting(
        @Json(name = "jumlah_spesimen_antigen") val antigenSamples: Int,
        @Json(name = "jumlah_orang_antigen") val antigenTested: Int,
        @Json(name = "jumlah_spesimen_pcr_tcm") val pcrSamples: Int,
        @Json(name = "jumlah_orang_pcr_tcm") val pcrTested: Int,
    )

    fun toCovidTestingOverviewData(): CovidTestingData {
        return CovidTestingData(
            updated = daily.date,
            antigen = CovidTestingData.CovidTestMethod(
                samples = CountStatistics(
                    added = daily.antigenSamples, total = total.antigenSamples
                ),
                peopleTested = CountStatistics(
                    added = daily.antigenTested, total = total.antigenTested
                )
            ),
            pcr = CovidTestingData.CovidTestMethod(
                samples = CountStatistics(
                    added = daily.pcrSamples, total = total.pcrSamples
                ),
                peopleTested = CountStatistics(
                    added = daily.pcrTested, total = total.pcrTested
                )
            ),
        )
    }
}
