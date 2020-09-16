package com.tilikki.bnccapp.siagacovid.overview

import com.tilikki.bnccapp.siagacovid.ApiCallModel

class OverviewModel : ApiCallModel {
    override val apiURL: String = lookupSummaryApiURL

    companion object {
        const val lookupSummaryApiURL = "https://api.kawalcorona.com/indonesia/"
    }
}