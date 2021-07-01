package com.tilikki.siagacovid.network

import com.tilikki.siagacovid.lookup.netmodel.RegionSummaryData
import com.tilikki.siagacovid.overview.netmodel.OverviewRootData
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

interface CovidGovernmentApiInterface {
    @GET("update.json")
    fun getCaseOverview(): Observable<Response<OverviewRootData>>

    @GET("prov.json")
    fun getRegionCaseOverview(): Observable<Response<RegionSummaryData>>
}
