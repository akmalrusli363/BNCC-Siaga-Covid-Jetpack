package com.tilikki.bnccapp.siagacovid.repository

import com.tilikki.bnccapp.siagacovid.view.worldstats.netdata.GlobalCovidSummary
import io.reactivex.Observable
import retrofit2.Response

interface GlobalCovidRepository : BaseDataRepository {
    fun getGlobalCovidSummary(): Observable<Response<GlobalCovidSummary>>
}
