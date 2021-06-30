package com.tilikki.bnccapp.siagacovid.view.worldstats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tilikki.bnccapp.siagacovid.model.CountryLookupData
import com.tilikki.bnccapp.siagacovid.model.WorldCaseOverview
import com.tilikki.bnccapp.siagacovid.repository.GlobalCovidRepositoryImpl
import com.tilikki.bnccapp.siagacovid.utils.StringParser
import com.tilikki.bnccapp.siagacovid.view.BaseViewModel

class WorldStatViewModel : BaseViewModel() {
    private val worldStatRepository = GlobalCovidRepositoryImpl()

    private var _worldCaseOverview: MutableLiveData<WorldCaseOverview> = MutableLiveData()
    val worldCaseOverview: LiveData<WorldCaseOverview>
        get() = _worldCaseOverview

    private var _countryLookupData: MutableLiveData<List<CountryLookupData>> = MutableLiveData()
    val countryLookupData: LiveData<List<CountryLookupData>>
        get() = _countryLookupData

    private var _dataSource: MutableLiveData<String> = MutableLiveData()
    val dataSource: LiveData<String>
        get() = _dataSource

    override fun fetchData() {
        _dataSource.postValue(getDataSource())
        fetchData(worldStatRepository.getGlobalCovidSummary(), {
            mapReactiveResponseData(it) { data ->
                _worldCaseOverview.postValue(data.global.toWorldCaseOverview())
                _countryLookupData.postValue(data.countries.map { countryData ->
                    countryData.toCountryLookupData()
                })
            }
        })
    }

    private fun getDataSource(): String {
        val baseUrl = StringParser.parseDomain(worldStatRepository.getBaseUrl())
        val baseProviderName = worldStatRepository.getDataProviderName()
        return "Data source: $baseProviderName ($baseUrl)"
    }
}
