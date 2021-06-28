package com.tilikki.bnccapp.siagacovid.repository

import com.tilikki.bnccapp.siagacovid.view.hotline.HotlineData
import io.reactivex.Observable
import retrofit2.Response

interface CovidHotlineRepository {
    fun getHotline(): Observable<Response<List<HotlineData>>>
}
