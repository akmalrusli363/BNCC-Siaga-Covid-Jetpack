package com.tilikki.bnccapp.siagacovid.network

import com.tilikki.bnccapp.siagacovid.worldstats.netdata.GlobalCovidSummary
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

interface GlobalCovidApiInterface {
    @GET("summary")
    fun getGlobalCovidSummary(): Observable<Response<GlobalCovidSummary>>
}
