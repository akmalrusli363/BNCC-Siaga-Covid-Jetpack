package com.tilikki.siagacovid.repository

import com.tilikki.siagacovid.view.lookup.netmodel.BnpbRegionalRootData
import io.reactivex.Observable
import retrofit2.Response

@Deprecated("Data provider is not longer updated")
interface BnpbCovidRepository : BaseDataRepository {
    fun getRegionalCaseOverview(): Observable<Response<List<BnpbRegionalRootData>>>
}
