package com.tilikki.bnccapp.siagacovid.hotline

import com.tilikki.bnccapp.siagacovid.ApiCallModel
import com.tilikki.bnccapp.siagacovid.repository.CovidHotlineRepository
import com.tilikki.bnccapp.siagacovid.repository.CovidHotlineRepositoryImpl
import retrofit2.HttpException

class HotlineModel : ApiCallModel<List<HotlineData>> {
    private val repository: CovidHotlineRepository = CovidHotlineRepositoryImpl()

    override fun fetchData(): List<HotlineData> {
        val response = repository.getHotline()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw HttpException(response)
        }
    }
}
