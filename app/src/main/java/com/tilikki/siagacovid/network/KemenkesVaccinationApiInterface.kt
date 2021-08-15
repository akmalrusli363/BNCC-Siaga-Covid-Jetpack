package com.tilikki.siagacovid.network

import com.tilikki.siagacovid.view.vaccine.netmodel.VaccinationRootData
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

interface KemenkesVaccinationApiInterface {
    @GET("vaksinasi")
    fun getVaccinationData(): Observable<Response<VaccinationRootData>>
}
