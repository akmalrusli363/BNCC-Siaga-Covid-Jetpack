package com.tilikki.siagacovid.repository

import com.tilikki.siagacovid.network.BnpbCovidApiInterface
import com.tilikki.siagacovid.view.lookup.netmodel.BnpbRegionalRootData
import io.reactivex.Observable
import retrofit2.Response

@Deprecated("Data provider is not longer updated")
class BnpbCovidRepositoryImpl : BnpbCovidRepository, BaseRepository() {
    private val apiInterface = retrofit.create(BnpbCovidApiInterface::class.java)

    override fun getDataProviderName(): String {
        return "BNPB Indonesia"
    }

    override fun getBaseUrl(): String {
        return "https://api.kawalcorona.com/indonesia/"
    }

    override fun getRegionalCaseOverview(): Observable<Response<List<BnpbRegionalRootData>>> {
        return apiInterface.getRegionalCase()
    }
}
