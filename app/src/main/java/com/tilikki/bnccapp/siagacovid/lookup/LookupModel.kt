package com.tilikki.bnccapp.siagacovid.lookup

import com.tilikki.bnccapp.siagacovid.ApiCallModel
import okhttp3.Response

abstract class LookupModel : ApiCallModel {
    override val apiURL: String by lazy { apiURL() }
    abstract fun apiURL(): String
    abstract fun apiProvider(): String
    abstract fun obtainData(response: Response): MutableList<LookupData>
}