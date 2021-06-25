package com.tilikki.bnccapp.siagacovid.hotline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tilikki.bnccapp.siagacovid.BaseViewModel
import com.tilikki.bnccapp.siagacovid.repository.CovidHotlineRepositoryImpl

class HotlineViewModel : BaseViewModel() {
    private var _hotlineList: MutableLiveData<List<HotlineData>> = MutableLiveData()
    val hotlineList: LiveData<List<HotlineData>>
        get() = _hotlineList

    override fun fetchData() {
        fetchData(CovidHotlineRepositoryImpl().getHotline(), {
            mapReactiveResponseData(it) { data ->
                _hotlineList.postValue(data)
            }
        })
    }
}
