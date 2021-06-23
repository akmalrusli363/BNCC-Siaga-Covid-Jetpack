package com.tilikki.bnccapp.siagacovid.repository

import com.squareup.moshi.Moshi
import com.tilikki.bnccapp.siagacovid.lookup.netmodel.RegionSummaryData
import com.tilikki.bnccapp.siagacovid.network.CovidGovernmentApiInterface
import com.tilikki.bnccapp.siagacovid.network.NetworkConstants
import com.tilikki.bnccapp.siagacovid.overview.netmodel.OverviewRootData
import com.tilikki.bnccapp.siagacovid.utils.jsonadapter.MoshiDateAdapter
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class CovidGovernmentRepositoryImpl : CovidGovernmentRepository {
    private val moshi = Moshi.Builder().add(MoshiDateAdapter()).build()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(NetworkConstants.GOVERNMENT_PROVIDED_COVID19_DATA)
        .build()
    private val apiInterface = retrofit.create(CovidGovernmentApiInterface::class.java)

    override fun getCaseOverview(): Observable<Response<OverviewRootData>> {
        return apiInterface.getCaseOverview()
    }

    override fun getRegionCaseOverview(): Observable<Response<RegionSummaryData>> {
        return apiInterface.getRegionCaseOverview()
    }
}
