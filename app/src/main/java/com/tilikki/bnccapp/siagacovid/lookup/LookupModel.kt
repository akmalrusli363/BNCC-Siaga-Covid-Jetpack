package com.tilikki.bnccapp.siagacovid.lookup

import com.tilikki.bnccapp.siagacovid.ApiCallModel

class LookupModel : ApiCallModel {
    override val apiURL: String = lookupDataApiURL

    companion object {
        const val lookupDataApiURL = "https://api.kawalcorona.com/indonesia/provinsi/"
    }
}