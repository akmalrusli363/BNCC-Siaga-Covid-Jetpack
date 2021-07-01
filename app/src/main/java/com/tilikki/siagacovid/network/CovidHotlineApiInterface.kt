package com.tilikki.siagacovid.network

import com.tilikki.siagacovid.view.hotline.HotlineData
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

interface CovidHotlineApiInterface {
    @GET("hotlines.json")
    fun getHotline(): Observable<Response<List<HotlineData>>>
}
