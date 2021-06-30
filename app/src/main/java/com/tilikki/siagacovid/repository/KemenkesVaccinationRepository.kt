package com.tilikki.siagacovid.repository

import com.tilikki.siagacovid.view.vaccine.netmodel.VaccinationRootData
import io.reactivex.Observable
import retrofit2.Response

interface KemenkesVaccinationRepository {
    fun getVaccinationData(): Observable<Response<VaccinationRootData>>
}
