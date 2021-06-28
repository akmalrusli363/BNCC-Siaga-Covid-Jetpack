package com.tilikki.bnccapp.siagacovid.repository

import com.tilikki.bnccapp.siagacovid.network.CovidGovernmentApiInterface
import com.tilikki.bnccapp.siagacovid.network.NetworkConstants
import com.tilikki.bnccapp.siagacovid.view.lookup.netmodel.RegionSummaryData
import com.tilikki.bnccapp.siagacovid.view.overview.netmodel.OverviewRootData
import io.reactivex.Observable
import retrofit2.Response

class CovidGovernmentRepositoryImpl : CovidGovernmentRepository, BaseRepository() {
    private val apiInterface = retrofit.create(CovidGovernmentApiInterface::class.java)

    override fun getDataProviderName(): String {
        return "data.covid19.go.id"
    }

    override fun getBaseUrl(): String {
        return NetworkConstants.GOVERNMENT_PROVIDED_COVID19_DATA
    }

    override fun getCaseOverview(): Observable<Response<OverviewRootData>> {
        return apiInterface.getCaseOverview()
    }

    override fun getRegionCaseOverview(): Observable<Response<RegionSummaryData>> {
        return apiInterface.getRegionCaseOverview()
    }
}
