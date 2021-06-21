package com.tilikki.bnccapp.siagacovid.lookup

import com.tilikki.bnccapp.siagacovid.ApiCallModel
import com.tilikki.bnccapp.siagacovid.lookup.netmodel.RegionSummaryData
import com.tilikki.bnccapp.siagacovid.repository.CovidGovernmentRepository
import com.tilikki.bnccapp.siagacovid.repository.CovidGovernmentRepositoryImpl
import retrofit2.HttpException

class LookupModel : ApiCallModel<RegionSummaryData> {
    private val repository: CovidGovernmentRepository = CovidGovernmentRepositoryImpl()

    override fun fetchData(): RegionSummaryData {
        val response = repository.getRegionCaseOverview()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw HttpException(response)
        }
    }
}
