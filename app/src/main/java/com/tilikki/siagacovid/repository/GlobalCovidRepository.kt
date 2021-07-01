package com.tilikki.siagacovid.repository

import com.tilikki.siagacovid.view.worldstats.netdata.GlobalCovidSummary
import io.reactivex.Observable
import retrofit2.Response

interface GlobalCovidRepository : BaseDataRepository {
    fun getGlobalCovidSummary(): Observable<Response<GlobalCovidSummary>>
}
