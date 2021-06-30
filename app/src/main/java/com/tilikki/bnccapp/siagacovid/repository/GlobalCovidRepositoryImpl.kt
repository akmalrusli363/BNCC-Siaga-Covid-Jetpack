package com.tilikki.bnccapp.siagacovid.repository

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tilikki.bnccapp.siagacovid.network.GlobalCovidApiInterface
import com.tilikki.bnccapp.siagacovid.view.worldstats.netdata.GlobalCovidSummary
import io.reactivex.Observable
import retrofit2.Response
import java.util.*

class GlobalCovidRepositoryImpl : BaseRepository(), GlobalCovidRepository {
    override val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(Date::class.java, Rfc3339DateJsonAdapter())
        .build()

    private val apiInterface = retrofit.create(GlobalCovidApiInterface::class.java)

    override fun getBaseUrl(): String {
        return "https://api.covid19api.com/"
    }

    override fun getDataProviderName(): String {
        return "Johns Hopkins University CSSE"
    }

    override fun getGlobalCovidSummary(): Observable<Response<GlobalCovidSummary>> {
        return apiInterface.getGlobalCovidSummary()
    }
}
