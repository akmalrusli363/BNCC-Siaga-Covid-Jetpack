package com.tilikki.siagacovid.network

import com.tilikki.siagacovid.view.lookup.netmodel.BnpbRegionalRootData
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

interface BnpbCovidApiInterface {
    @GET("provinsi")
    fun getRegionalCase(): Observable<Response<List<BnpbRegionalRootData>>>
}
