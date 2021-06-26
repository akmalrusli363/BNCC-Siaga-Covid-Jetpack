package com.tilikki.bnccapp.siagacovid.view.lookup.netmodel

import com.squareup.moshi.Json
import com.tilikki.bnccapp.siagacovid.model.CountStatistics
import com.tilikki.bnccapp.siagacovid.model.RegionLookupData

data class BnpbRegionalRootData(
    @Json(name = "attributes") val data: BnpbRegionalData
) {
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
