package com.tilikki.bnccapp.siagacovid.repository

import com.squareup.moshi.Moshi
import com.tilikki.bnccapp.siagacovid.hotline.HotlineData
import com.tilikki.bnccapp.siagacovid.network.CovidHotlineApiInterface
import com.tilikki.bnccapp.siagacovid.network.NetworkConstants
import com.tilikki.bnccapp.siagacovid.utils.jsonadapter.MoshiDateAdapter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class CovidHotlineRepositoryImpl : CovidHotlineRepository {
    private val moshi = Moshi.Builder().add(MoshiDateAdapter()).build()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(NetworkConstants.HOTLINE_COVID19)
        .build()
    private val apiInterface = retrofit.create(CovidHotlineApiInterface::class.java)

    override fun getHotline(): Response<List<HotlineData>> {
        return apiInterface.getHotline().execute()
    }
}
