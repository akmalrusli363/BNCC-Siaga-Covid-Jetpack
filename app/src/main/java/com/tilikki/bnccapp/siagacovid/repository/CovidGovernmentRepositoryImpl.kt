package com.tilikki.bnccapp.siagacovid.repository

import com.squareup.moshi.Moshi
import com.tilikki.bnccapp.siagacovid.lookup.netmodel.RegionSummaryData
import com.tilikki.bnccapp.siagacovid.network.CovidGovernmentApiInterface
import com.tilikki.bnccapp.siagacovid.network.NetworkConstants
import com.tilikki.bnccapp.siagacovid.overview.netmodel.OverviewRootData
import com.tilikki.bnccapp.siagacovid.utils.jsonadapter.MoshiDateAdapter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class CovidGovernmentRepositoryImpl : CovidGovernmentRepository {
    private val moshi = Moshi.Builder().add(MoshiDateAdapter()).build()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(NetworkConstants.GOVERNMENT_PROVIDED_COVID19_DATA)
        .build()
    private val apiInterface = retrofit.create(CovidGovernmentApiInterface::class.java)

    override fun getCaseOverview(): Response<OverviewRootData> {
        return apiInterface.getCaseOverview().execute()
    }

    override fun getRegionCaseOverview(): Response<RegionSummaryData> {
        return apiInterface.getRegionCaseOverview().execute()
    }
}
