package com.tilikki.bnccapp.siagacovid

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tilikki.bnccapp.siagacovid.utils.AppEventLogging
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import retrofit2.Response

abstract class BaseViewModel : ViewModel() {
    data class FetchResponse(
        val success: Boolean,
        val error: Throwable?,
    )

    private var _successResponse: MutableLiveData<FetchResponse> = MutableLiveData()
    val successResponse: LiveData<FetchResponse>
        get() = _successResponse

    abstract fun fetchData()

    protected inline fun <T> mapReactiveResponseData(
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

    protected fun <T> fetchData(
        observable: Observable<T>,
        onSuccess: (T) -> Unit,
        onFail: (Throwable) -> Unit = {}
    ) {
        observable.run {
            this.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onSuccess(it)
                    setSuccessResponse(true, null)
                }, {
                    Log.e(AppEventLogging.FETCH_FAILURE, it.localizedMessage, it)
                    onFail(it)
                    setSuccessResponse(false, it)
                })
        }
    }

    private fun setSuccessResponse(success: Boolean, error: Throwable?) {
        _successResponse.postValue(FetchResponse(success, error))
    }
}
