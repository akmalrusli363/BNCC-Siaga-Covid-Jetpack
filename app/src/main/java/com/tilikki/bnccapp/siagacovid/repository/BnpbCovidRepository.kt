package com.tilikki.bnccapp.siagacovid.repository

import com.tilikki.bnccapp.siagacovid.view.lookup.netmodel.BnpbRegionalRootData
import io.reactivex.Observable
import retrofit2.Response

interface BnpbCovidRepository : BaseDataRepository {
    fun getRegionalCaseOverview(): Observable<Response<List<BnpbRegionalRootData>>>
}
