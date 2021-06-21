package com.tilikki.bnccapp.siagacovid.network

import com.tilikki.bnccapp.siagacovid.hotline.HotlineData
import retrofit2.Call
import retrofit2.http.GET

interface CovidHotlineApiInterface {
    @GET("/hotline.json")
    fun getHotline(): Call<List<HotlineData>>
}
