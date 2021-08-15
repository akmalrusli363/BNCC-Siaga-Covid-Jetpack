package com.tilikki.siagacovid.repository

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tilikki.siagacovid.network.KemenkesVaccinationApiInterface
import com.tilikki.siagacovid.utils.jsonadapter.MoshiCustomIsoDateAdapter
import com.tilikki.siagacovid.view.vaccine.netmodel.VaccinationRootData
import io.reactivex.Observable
import retrofit2.Response

class KemenkesVaccinationRepositoryImpl : KemenkesVaccinationRepository, BaseRepository() {
    override val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .add(MoshiCustomIsoDateAdapter())
        .build()

    private val apiInterface = retrofit.create(KemenkesVaccinationApiInterface::class.java)

    override fun getBaseUrl(): String {
        return "https://cekdiri.id"
    }

    override fun getVaccinationData(): Observable<Response<VaccinationRootData>> {
        return apiInterface.getVaccinationData()
    }
}
