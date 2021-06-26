package com.tilikki.bnccapp.siagacovid.view.lookup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tilikki.bnccapp.siagacovid.model.RegionLookupData
import com.tilikki.bnccapp.siagacovid.repository.BnpbCovidRepository
import com.tilikki.bnccapp.siagacovid.repository.BnpbCovidRepositoryImpl
import com.tilikki.bnccapp.siagacovid.repository.CovidGovernmentRepository
import com.tilikki.bnccapp.siagacovid.repository.CovidGovernmentRepositoryImpl
import com.tilikki.bnccapp.siagacovid.view.BaseViewModel
import java.util.*

class LookupViewModel(provideFromGovernment: Boolean) : BaseViewModel() {
    private var _regionData: MutableLiveData<List<RegionLookupData>> = MutableLiveData()
    val regionData: LiveData<List<RegionLookupData>>
        get() = _regionData

    private var _lastUpdated: MutableLiveData<Date?> = MutableLiveData()
    val lastUpdated: LiveData<Date?>
        get() = _lastUpdated

    private var _fromGovernment: MutableLiveData<Boolean> = MutableLiveData(provideFromGovernment)
    val fromGovernment: LiveData<Boolean>
        get() = _fromGovernment

    private var _dataSource: MutableLiveData<String> = MutableLiveData()
    val dataSource: LiveData<String>
        get() = _dataSource

    private val covidGovernmentRepo: CovidGovernmentRepository = CovidGovernmentRepositoryImpl()
    private val bnpbCovidRepo: BnpbCovidRepository = BnpbCovidRepositoryImpl()

    override fun fetchData() {
        if (_fromGovernment.value == false) {
            fetchAlternativeSource()
        } else {
            fetchGovernmentSource()
        }
    }

    fun toggleDataSource(fromGovernment: Boolean) {
        _fromGovernment.postValue(fromGovernment)
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

    private fun fetchAlternativeSource() {
        _dataSource.postValue(bnpbCovidRepo.getDataProviderName())
        fetchData(bnpbCovidRepo.getRegionalCaseOverview(), {
            mapReactiveResponseData(it) { data ->
                _regionData.postValue(data.map { regionData ->
                    regionData.toRegionLookupData()
                })
                _lastUpdated.postValue(null)
            }
        })
    }
}
