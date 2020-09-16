package com.tilikki.bnccapp.siagacovid

import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request

interface PVContract {
    interface Presenter {
        fun fetchData()
        fun getData(): Callback
    }

    interface View<E> {
        fun updateData(listData: List<E>)
        fun showError(tag: String, e: Exception)
    }

    interface ObjectView<T> {
        fun updateData(listData: T)
        fun showError(tag: String, e: Exception)
    }
}