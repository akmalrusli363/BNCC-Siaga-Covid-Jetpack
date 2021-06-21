package com.tilikki.bnccapp.siagacovid.overview

import com.tilikki.bnccapp.siagacovid.ApiCallModel
import com.tilikki.bnccapp.siagacovid.model.CaseOverview
import com.tilikki.bnccapp.siagacovid.repository.CovidGovernmentRepositoryImpl
import retrofit2.HttpException

class OverviewModel : ApiCallModel<CaseOverview> {
    @Throws(Exception::class)
    override fun fetchData(): CaseOverview {
        val response = CovidGovernmentRepositoryImpl().getCaseOverview()
        if (response.isSuccessful) {
            return response.body()!!.update.toCaseOverview()
        } else {
            throw HttpException(response)
        }
    }
}
