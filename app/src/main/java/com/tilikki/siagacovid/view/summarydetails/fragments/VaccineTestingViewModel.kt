package com.tilikki.siagacovid.view.summarydetails.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tilikki.siagacovid.model.CovidTestingData
import com.tilikki.siagacovid.model.VaccinationOverviewData
import com.tilikki.siagacovid.repository.CovidGovernmentRepository
import com.tilikki.siagacovid.repository.CovidGovernmentRepositoryImpl
import com.tilikki.siagacovid.view.BaseViewModel

class VaccineTestingViewModel : BaseViewModel() {

    private var _isFetched: Boolean = false
    val isFetched: Boolean
        get() = _isFetched

    private var _tracingHistory: MutableLiveData<List<CovidTestingData>> = MutableLiveData()
    val tracingHistory: LiveData<List<CovidTestingData>>
        get() = _tracingHistory

    private var _vaccinationHistory: MutableLiveData<List<VaccinationOverviewData>> = MutableLiveData()
    val vaccinationHistory: LiveData<List<VaccinationOverviewData>>
        get() = _vaccinationHistory

    private val governmentRepository: CovidGovernmentRepository = CovidGovernmentRepositoryImpl()

    override fun fetchData() {
        fetchData(governmentRepository.getTestingVaccinationOverview(), {
            mapReactiveResponseData(it) { data ->
                _tracingHistory.postValue(data.testing.history.map { hist ->
                    hist.toCovidTestingOverviewData()
                })
                _vaccinationHistory.postValue(data.vaccination.history.map { hist ->
                    hist.toVaccinationOverviewData()
                })
                _isFetched = true
            }
        })
    }

}
