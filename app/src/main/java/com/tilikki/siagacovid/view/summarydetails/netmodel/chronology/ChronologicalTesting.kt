package com.tilikki.siagacovid.view.summarydetails.netmodel.chronology

import com.squareup.moshi.Json
import com.tilikki.siagacovid.model.CountStatistics
import com.tilikki.siagacovid.model.CovidTestingData
import java.util.*

data class ChronologicalTesting(
    @Json(name = "key") val date: Long,
    @Json(name = "key_as_string") val dateString: String,
    @Json(name = "jumlah_spesimen_antigen") val antigenSamples: JsonValue,
    @Json(name = "jumlah_orang_antigen") val antigenTested: JsonValue,
    @Json(name = "jumlah_spesimen_pcr_tcm") val pcrSamples: JsonValue,
    @Json(name = "jumlah_orang_pcr_tcm") val pcrTested: JsonValue,
    @Json(name = "jumlah_spesimen_antigen_kum") val cumulativeAntigenSamples: JsonValue,
    @Json(name = "jumlah_orang_antigen_kum") val cumulativeAntigenTested: JsonValue,
    @Json(name = "jumlah_spesimen_pcr_tcm_kum") val cumulativePcrSamples: JsonValue,
    @Json(name = "jumlah_orang_pcr_tcm_kum") val cumulativePcrTested: JsonValue,
) {
    fun toCovidTestingOverviewData(): CovidTestingData {
        return CovidTestingData(
            updated = Date(date),
            antigen = CovidTestingData.CovidTestMethod(
                samples = mapToCountStatistics(cumulativeAntigenSamples, antigenSamples),
                peopleTested = mapToCountStatistics(cumulativeAntigenTested, antigenTested)
            ),
            pcr = CovidTestingData.CovidTestMethod(
                samples = mapToCountStatistics(cumulativePcrSamples, pcrSamples),
                peopleTested = mapToCountStatistics(cumulativePcrTested, pcrTested)
            ),
        )
    }

    private fun mapToCountStatistics(total: JsonValue, daily: JsonValue): CountStatistics {
        return CountStatistics(total = total.value, added = daily.value)
    }
}
