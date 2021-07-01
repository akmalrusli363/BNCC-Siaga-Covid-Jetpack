package com.tilikki.siagacovid

interface PVContract {
    interface Presenter {
        fun fetchData()
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
