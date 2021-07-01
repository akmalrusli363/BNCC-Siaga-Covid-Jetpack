package com.tilikki.siagacovid.repository

import com.tilikki.siagacovid.network.CovidHotlineApiInterface
import com.tilikki.siagacovid.network.NetworkConstants
import com.tilikki.siagacovid.view.hotline.HotlineData
import io.reactivex.Observable
import retrofit2.Response

class CovidHotlineRepositoryImpl : CovidHotlineRepository, BaseRepository() {
    private val apiInterface = retrofit.create(CovidHotlineApiInterface::class.java)

    override fun getBaseUrl(): String {
        return NetworkConstants.HOTLINE_COVID19
    }

    override fun getHotline(): Observable<Response<List<HotlineData>>> {
        return apiInterface.getHotline()
    }
}
