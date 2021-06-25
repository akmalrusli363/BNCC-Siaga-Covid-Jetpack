package com.tilikki.bnccapp.siagacovid.worldstats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tilikki.bnccapp.siagacovid.BaseViewModel
import com.tilikki.bnccapp.siagacovid.model.CountryLookupData
import com.tilikki.bnccapp.siagacovid.model.WorldCaseOverview
import com.tilikki.bnccapp.siagacovid.repository.GlobalCovidRepositoryImpl
import retrofit2.HttpException

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
            if (it.isSuccessful && it.body() != null) {
                val body = it.body()!!
                _worldCaseOverview.postValue(body.global.toWorldCaseOverview())
                _countryLookupData.postValue(body.countries.map { countryData ->
                    countryData.toCountryLookupData()
                })
            } else {
                throw HttpException(it)
            }
        })
    }
}
