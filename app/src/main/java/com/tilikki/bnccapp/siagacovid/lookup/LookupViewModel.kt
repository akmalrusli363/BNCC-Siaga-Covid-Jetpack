package com.tilikki.bnccapp.siagacovid.lookup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tilikki.bnccapp.siagacovid.BaseViewModel
import com.tilikki.bnccapp.siagacovid.model.RegionLookupData
import com.tilikki.bnccapp.siagacovid.repository.CovidGovernmentRepositoryImpl
import java.util.*

class LookupViewModel : BaseViewModel() {
    private var _regionData: MutableLiveData<List<RegionLookupData>> = MutableLiveData()
    val regionData: LiveData<List<RegionLookupData>>
        get() = _regionData

    private var _lastUpdated: MutableLiveData<Date> = MutableLiveData()
    val lastUpdated: LiveData<Date>
        get() = _lastUpdated

    override fun fetchData() {
        fetchData(CovidGovernmentRepositoryImpl().getRegionCaseOverview(), {
            mapReactiveResponseData(it) { data ->
                _regionData.postValue(data.regionData.map { regionData ->
                    regionData.toRegionLookupData()
                })
                _lastUpdated.postValue(data.lastUpdated)
            }
        })
    }
}
