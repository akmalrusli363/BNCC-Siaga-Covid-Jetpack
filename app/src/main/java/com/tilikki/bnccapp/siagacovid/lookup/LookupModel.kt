package com.tilikki.bnccapp.siagacovid.lookup

import com.tilikki.bnccapp.siagacovid.ApiCallModel

class LookupModel : ApiCallModel {
    override val apiURL: String = lookupDataApiURL

    companion object {
        const val lookupDataApiURL = "https://data.covid19.go.id/public/api/prov.json"
    }
}