package com.tilikki.bnccapp.siagacovid.worldstats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tilikki.bnccapp.siagacovid.BaseViewModel
import com.tilikki.bnccapp.siagacovid.model.CountryLookupData
import com.tilikki.bnccapp.siagacovid.model.WorldCaseOverview
import com.tilikki.bnccapp.siagacovid.repository.GlobalCovidRepositoryImpl

class WorldStatViewModel : BaseViewModel() {
    private val worldStatRepository = GlobalCovidRepositoryImpl()

    private var _worldCaseOverview: MutableLiveData<WorldCaseOverview> = MutableLiveData()
    val worldCaseOverview: LiveData<WorldCaseOverview>
        get() = _worldCaseOverview

    private var _countryLookupData: MutableLiveData<List<CountryLookupData>> = MutableLiveData()
    val countryLookupData: LiveData<List<CountryLookupData>>
        get() = _countryLookupData

    override fun fetchData() {
        fetchData(worldStatRepository.getGlobalCovidSummary(), {
            mapReactiveResponseData(it) { data ->
                _worldCaseOverview.postValue(data.global.toWorldCaseOverview())
                _countryLookupData.postValue(data.countries.map { countryData ->
                    countryData.toCountryLookupData()
                })
            }
        })
    }
}
