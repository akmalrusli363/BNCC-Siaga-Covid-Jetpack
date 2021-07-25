package com.tilikki.siagacovid.network

import com.tilikki.siagacovid.view.lookup.netmodel.RegionSummaryData
import com.tilikki.siagacovid.view.overview.netmodel.OverviewRootData
import com.tilikki.siagacovid.view.summarydetails.netmodel.TestingVaccinationRootData
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

interface CovidGovernmentApiInterface {
    @GET("update.json")
    fun getCaseOverview(): Observable<Response<OverviewRootData>>

    @GET("prov.json")
    fun getRegionCaseOverview(): Observable<Response<RegionSummaryData>>

    @GET("pemeriksaan-vaksinasi.json")
    fun getTestingVaccinationOverview(): Observable<Response<TestingVaccinationRootData>>
}
