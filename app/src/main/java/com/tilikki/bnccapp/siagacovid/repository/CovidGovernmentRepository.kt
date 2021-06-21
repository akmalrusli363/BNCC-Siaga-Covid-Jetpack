package com.tilikki.bnccapp.siagacovid.repository

import com.tilikki.bnccapp.siagacovid.lookup.netmodel.RegionSummaryData
import com.tilikki.bnccapp.siagacovid.overview.netmodel.OverviewRootData
import retrofit2.Response

interface CovidGovernmentRepository {
    fun getCaseOverview(): Response<OverviewRootData>
    fun getRegionCaseOverview(): Response<RegionSummaryData>
}
