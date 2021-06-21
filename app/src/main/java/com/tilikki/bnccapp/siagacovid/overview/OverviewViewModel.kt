package com.tilikki.bnccapp.siagacovid.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tilikki.bnccapp.siagacovid.BaseViewModel
import com.tilikki.bnccapp.siagacovid.model.CaseOverview
import com.tilikki.bnccapp.siagacovid.repository.CovidGovernmentRepositoryImpl
import retrofit2.HttpException

class OverviewViewModel: BaseViewModel() {
    private var _overviewData: MutableLiveData<CaseOverview> = MutableLiveData()
    val overviewData: LiveData<CaseOverview>
        get() = _overviewData

    override fun fetchData() {
        fetchData(CovidGovernmentRepositoryImpl().getCaseOverview(), {
            if (it.isSuccessful && it.body() != null) {
                val data = it.body()!!
                _overviewData.postValue(data.update.toCaseOverview())
            } else {
                throw HttpException(it)
            }
        })
    }
}
