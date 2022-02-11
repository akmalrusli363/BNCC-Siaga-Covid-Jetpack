package com.tilikki.siagacovid.view.lookup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tilikki.siagacovid.model.RegionLookupData
import com.tilikki.siagacovid.repository.CovidGovernmentRepository
import com.tilikki.siagacovid.repository.CovidGovernmentRepositoryImpl
import com.tilikki.siagacovid.view.BaseViewModel
import java.util.*

class LookupViewModel() : BaseViewModel() {
    private var _regionData: MutableLiveData<List<RegionLookupData>> = MutableLiveData()
    val regionData: LiveData<List<RegionLookupData>>
        get() = _regionData

    private var _lastUpdated: MutableLiveData<Date?> = MutableLiveData()
    val lastUpdated: LiveData<Date?>
        get() = _lastUpdated

    private var _dataSource: MutableLiveData<String> = MutableLiveData()
    val dataSource: LiveData<String>
        get() = _dataSource

    private val covidGovernmentRepo: CovidGovernmentRepository = CovidGovernmentRepositoryImpl()

    override fun fetchData() {
        fetchGovernmentSource()
    }

    private fun fetchGovernmentSource() {
        _dataSource.postValue(covidGovernmentRepo.getDataProviderName())
        fetchData(covidGovernmentRepo.getRegionCaseOverview(), {
            mapReactiveResponseData(it) { data ->
                _regionData.postValue(data.regionData.map { regionData ->
                    regionData.toRegionLookupData()
                })
                _lastUpdated.postValue(data.lastUpdated)
            }
        })
    }
}
