package com.tilikki.bnccapp.siagacovid.lookup.netmodel

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tilikki.bnccapp.siagacovid.model.CountStatistics
import com.tilikki.bnccapp.siagacovid.model.RegionLookupData

@JsonClass(generateAdapter = true)
data class BnpbRegionalRootData(
    @Json(name = "attributes") val data: BnpbRegionalData
) {
    @JsonClass(generateAdapter = true)
    data class BnpbRegionalData(
        @Json(name = "Provinsi") val province: String,
        @Json(name = "Kode_Provi") val provinceCode: Int,
        @Json(name = "Kasus_Posi") val confirmedCase: Int,
        @Json(name = "Kasus_Semb") val recoveredCase: Int,
        @Json(name = "Kasus_Meni") val deathCase: Int,
    )

    fun toRegionLookupData(): RegionLookupData {
        return RegionLookupData(
            province = data.province,
            confirmedCase = CountStatistics(data.confirmedCase, null),
            recoveredCase = CountStatistics(data.recoveredCase, null),
            deathCase = CountStatistics(data.deathCase, null),
        )
    }
}
