package com.tilikki.bnccapp.siagacovid

import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request

interface ApiCallModel {
    private val okHttpClient: OkHttpClient
        get() = OkHttpClient()

    val apiURL: String

    fun fetchData(callback: Callback) {
        val request: Request = Request.Builder().url(apiURL).build()
        okHttpClient.newCall(request).enqueue(callback)
    }
}
