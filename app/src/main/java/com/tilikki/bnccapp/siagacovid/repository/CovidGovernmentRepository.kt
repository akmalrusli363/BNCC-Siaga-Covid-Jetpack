package com.tilikki.bnccapp.siagacovid.repository

import com.tilikki.bnccapp.siagacovid.view.lookup.netmodel.RegionSummaryData
import com.tilikki.bnccapp.siagacovid.view.overview.netmodel.OverviewRootData
import io.reactivex.Observable
import retrofit2.Response

interface CovidGovernmentRepository : BaseDataRepository {
    fun getCaseOverview(): Observable<Response<OverviewRootData>>
    fun getRegionCaseOverview(): Observable<Response<RegionSummaryData>>
}
