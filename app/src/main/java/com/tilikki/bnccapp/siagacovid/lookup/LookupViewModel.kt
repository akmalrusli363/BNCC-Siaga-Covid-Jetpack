package com.tilikki.bnccapp.siagacovid.lookup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tilikki.bnccapp.siagacovid.BaseViewModel
import com.tilikki.bnccapp.siagacovid.model.RegionLookupData
import com.tilikki.bnccapp.siagacovid.repository.BnpbCovidRepository
import com.tilikki.bnccapp.siagacovid.repository.BnpbCovidRepositoryImpl
import com.tilikki.bnccapp.siagacovid.repository.CovidGovernmentRepository
import com.tilikki.bnccapp.siagacovid.repository.CovidGovernmentRepositoryImpl
import retrofit2.HttpException
import retrofit2.Response
import java.util.*

class LookupViewModel : BaseViewModel() {
    private var _regionData: MutableLiveData<List<RegionLookupData>> = MutableLiveData()
    val regionData: LiveData<List<RegionLookupData>>
        get() = _regionData

    private var _lastUpdated: MutableLiveData<Date?> = MutableLiveData()
    val lastUpdated: LiveData<Date?>
        get() = _lastUpdated

    private var _fromGovernment: MutableLiveData<Boolean> = MutableLiveData()
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
        fetchData(covidGovernmentRepo.getRegionCaseOverview(), {
            mapReactiveResponseData(it) { data ->
                _regionData.postValue(data.regionData.map { regionData ->
                    regionData.toRegionLookupData()
                })
                _lastUpdated.postValue(data.lastUpdated)
            }
        })
        _dataSource.postValue(covidGovernmentRepo.getDataProviderName())
    }

    private fun fetchAlternativeSource() {
        fetchData(bnpbCovidRepo.getRegionalCaseOverview(), {
            mapReactiveResponseData(it) { data ->
                _regionData.postValue(data.map { regionData ->
                    regionData.toRegionLookupData()
                })
                _lastUpdated.postValue(null)
            }
        })
        _dataSource.postValue(bnpbCovidRepo.getDataProviderName())
    }

    private inline fun <T> mapReactiveResponseData(
        response: Response<T>,
        onSuccessAction: (T) -> Unit
    ) {
        if (response.isSuccessful && response.body() != null) {
            val data = response.body()!!
            onSuccessAction(data)
        } else {
            throw HttpException(response)
        }
    }
}
