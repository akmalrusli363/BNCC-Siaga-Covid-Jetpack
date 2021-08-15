package com.tilikki.siagacovid.view.vaccine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tilikki.siagacovid.model.VaccinationOverview
import com.tilikki.siagacovid.repository.KemenkesVaccinationRepositoryImpl
import com.tilikki.siagacovid.view.BaseViewModel
import java.util.*

class VaccinationViewModel : BaseViewModel() {
    private val repository = KemenkesVaccinationRepositoryImpl()

    private var _vaccinationData: MutableLiveData<VaccinationOverview> = MutableLiveData()
    val vaccinationData: LiveData<VaccinationOverview>
        get() = _vaccinationData

    private var _lastUpdated: MutableLiveData<Date> = MutableLiveData()
    val lastUpdated: LiveData<Date>
        get() = _lastUpdated

    override fun fetchData() {
        fetchData(repository.getVaccinationData(), {
            mapReactiveResponseData(it) { data ->
                val latestVaccinationOverview = data.monitoring
                    .last().toVaccinationOverview()
                _vaccinationData.postValue(latestVaccinationOverview)
                _lastUpdated.postValue(data.lastUpdated)
            }
        })
    }
}
