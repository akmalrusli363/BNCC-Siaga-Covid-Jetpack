package com.tilikki.siagacovid.view.summarydetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tilikki.siagacovid.model.CaseOverview
import com.tilikki.siagacovid.model.CovidTestingData
import com.tilikki.siagacovid.model.VaccinationOverviewData
import com.tilikki.siagacovid.repository.CovidGovernmentRepository
import com.tilikki.siagacovid.repository.CovidGovernmentRepositoryImpl
import com.tilikki.siagacovid.view.BaseViewModel

class CaseSummaryDetailViewModel : BaseViewModel() {
    private var _caseOverview: MutableLiveData<CaseOverview> = MutableLiveData()
    val caseOverview: LiveData<CaseOverview>
        get() = _caseOverview

    private var _testTracing: MutableLiveData<CovidTestingData> = MutableLiveData()
    val testTracing: LiveData<CovidTestingData>
        get() = _testTracing

    private var _vaccinationOverview: MutableLiveData<VaccinationOverviewData> = MutableLiveData()
    val vaccinationOverview: LiveData<VaccinationOverviewData>
        get() = _vaccinationOverview

    private val governmentRepository: CovidGovernmentRepository = CovidGovernmentRepositoryImpl()

    override fun fetchData() {
        fetchData(governmentRepository.getCaseOverview(), {
            mapReactiveResponseData(it) { data ->
                _caseOverview.postValue(data.update.toCaseOverview())
            }
        })
        fetchData(governmentRepository.getTestingVaccinationOverview(), {
            mapReactiveResponseData(it) { data ->
                _testTracing.postValue(data.testing.toCovidTestingOverviewData())
                _vaccinationOverview.postValue(data.vaccination.toVaccinationOverviewData())
            }
        })
    }
}
