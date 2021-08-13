package com.tilikki.siagacovid.view.summarydetails.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tilikki.siagacovid.model.CaseOverview
import com.tilikki.siagacovid.repository.CovidGovernmentRepository
import com.tilikki.siagacovid.repository.CovidGovernmentRepositoryImpl
import com.tilikki.siagacovid.view.BaseViewModel

class CaseHistoryViewModel : BaseViewModel() {

    private var _isFetched: Boolean = false
    val isFetched: Boolean
        get() = _isFetched

    private var _caseHistory: MutableLiveData<List<CaseOverview>> = MutableLiveData()
    val caseHistory: LiveData<List<CaseOverview>>
        get() = _caseHistory

    private val governmentRepository: CovidGovernmentRepository = CovidGovernmentRepositoryImpl()

    override fun fetchData() {
        fetchData(governmentRepository.getCaseHistory(), {
            mapReactiveResponseData(it) { data ->
                _caseHistory.postValue(data.update.toHistoricalCaseOverview())
                _isFetched = true
            }
        })
    }

}
