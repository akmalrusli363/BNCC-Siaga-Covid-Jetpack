package com.tilikki.bnccapp.siagacovid

import okhttp3.Callback

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
        fun updateData(objectData: T)
        fun showError(tag: String, e: Exception)
    }
}