package com.tilikki.bnccapp.siagacovid.repository

import com.tilikki.bnccapp.siagacovid.network.BnpbCovidApiInterface
import com.tilikki.bnccapp.siagacovid.view.lookup.netmodel.BnpbRegionalRootData
import io.reactivex.Observable
import retrofit2.Response

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
