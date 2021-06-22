package com.tilikki.bnccapp.siagacovid.hotline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tilikki.bnccapp.siagacovid.BaseViewModel
import com.tilikki.bnccapp.siagacovid.repository.CovidHotlineRepositoryImpl
import retrofit2.HttpException

class HotlineViewModel : BaseViewModel() {
    private var _hotlineList: MutableLiveData<List<HotlineData>> = MutableLiveData()
    val hotlineList: LiveData<List<HotlineData>>
        get() = _hotlineList

    override fun fetchData() {
        fetchData(CovidHotlineRepositoryImpl().getHotline(), {
            if (it.isSuccessful && it.body() != null) {
                val data = it.body()!!
                _hotlineList.postValue(data)
            } else {
                throw HttpException(it)
            }
        })
    }
}
