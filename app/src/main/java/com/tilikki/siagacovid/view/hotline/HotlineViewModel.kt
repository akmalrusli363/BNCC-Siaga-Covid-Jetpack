package com.tilikki.siagacovid.view.hotline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tilikki.siagacovid.repository.CovidHotlineRepositoryImpl
import com.tilikki.siagacovid.view.BaseViewModel

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
