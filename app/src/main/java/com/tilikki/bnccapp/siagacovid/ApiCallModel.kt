package com.tilikki.bnccapp.siagacovid

interface ApiCallModel<T> {
    @Throws(Exception::class)
    fun fetchData(): T
}
