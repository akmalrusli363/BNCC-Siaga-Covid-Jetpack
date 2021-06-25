package com.tilikki.bnccapp.siagacovid.repository

import com.tilikki.bnccapp.siagacovid.worldstats.netdata.GlobalCovidSummary
import io.reactivex.Observable
import retrofit2.Response

interface GlobalCovidRepository {
    fun getGlobalCovidSummary(): Observable<Response<GlobalCovidSummary>>
}
