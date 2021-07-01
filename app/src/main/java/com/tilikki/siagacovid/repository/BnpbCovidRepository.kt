package com.tilikki.siagacovid.repository

import com.tilikki.siagacovid.view.lookup.netmodel.BnpbRegionalRootData
import io.reactivex.Observable
import retrofit2.Response

interface BnpbCovidRepository : BaseDataRepository {
    fun getRegionalCaseOverview(): Observable<Response<List<BnpbRegionalRootData>>>
}
