package com.tilikki.bnccapp.siagacovid.repository

import com.tilikki.bnccapp.siagacovid.lookup.netmodel.BnpbRegionalRootData
import io.reactivex.Observable
import retrofit2.Response

interface BnpbCovidRepository : CaseDataRepository {
    fun getRegionalCaseOverview(): Observable<Response<List<BnpbRegionalRootData>>>
}
