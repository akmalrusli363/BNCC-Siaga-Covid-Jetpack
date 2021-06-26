package com.tilikki.bnccapp.siagacovid.view.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tilikki.bnccapp.siagacovid.model.CaseOverview
import com.tilikki.bnccapp.siagacovid.repository.CovidGovernmentRepositoryImpl
import com.tilikki.bnccapp.siagacovid.view.BaseViewModel

class OverviewViewModel : BaseViewModel() {
    private var _overviewData: MutableLiveData<CaseOverview> = MutableLiveData()
    val overviewData: LiveData<CaseOverview>
        get() = _overviewData

    override fun fetchData() {
        fetchData(CovidGovernmentRepositoryImpl().getCaseOverview(), {
            mapReactiveResponseData(it) { data ->
                _overviewData.postValue(data.update.toCaseOverview())
            }
        })
    }
}
