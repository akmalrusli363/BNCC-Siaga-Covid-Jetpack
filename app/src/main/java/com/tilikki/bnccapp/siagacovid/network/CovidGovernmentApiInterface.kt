package com.tilikki.bnccapp.siagacovid.network

import com.tilikki.bnccapp.siagacovid.lookup.netmodel.RegionSummaryData
import com.tilikki.bnccapp.siagacovid.overview.netmodel.OverviewRootData
import retrofit2.Call
import retrofit2.http.GET

interface CovidGovernmentApiInterface {
    @GET("/update.json")
    fun getCaseOverview(): Call<OverviewRootData>

    @GET("/prov.json")
    fun getRegionCaseOverview(): Call<RegionSummaryData>
}
