package com.tilikki.bnccapp.siagacovid.overview

import com.tilikki.bnccapp.siagacovid.ApiCallModel

class OverviewModel : ApiCallModel {
    override val apiURL: String = lookupSummaryApiURL

    companion object {
        const val lookupSummaryApiURL = "https://data.covid19.go.id/public/api/update.json"
    }
}