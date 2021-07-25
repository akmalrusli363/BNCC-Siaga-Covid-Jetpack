package com.tilikki.siagacovid.repository

import com.tilikki.siagacovid.view.lookup.netmodel.RegionSummaryData
import com.tilikki.siagacovid.view.overview.netmodel.OverviewRootData
import com.tilikki.siagacovid.view.summarydetails.netmodel.TestingVaccinationRootData
import io.reactivex.Observable
import retrofit2.Response

interface CovidGovernmentRepository : BaseDataRepository {
    fun getCaseOverview(): Observable<Response<OverviewRootData>>
    fun getRegionCaseOverview(): Observable<Response<RegionSummaryData>>
    fun getTestingVaccinationOverview(): Observable<Response<TestingVaccinationRootData>>
}
