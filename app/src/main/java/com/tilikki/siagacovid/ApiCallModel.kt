package com.tilikki.siagacovid

interface ApiCallModel<T> {
    @Throws(Exception::class)
    fun fetchData(): T
}
