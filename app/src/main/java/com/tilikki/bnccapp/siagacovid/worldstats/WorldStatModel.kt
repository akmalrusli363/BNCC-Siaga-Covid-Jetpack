package com.tilikki.bnccapp.siagacovid.worldstats

import com.tilikki.bnccapp.siagacovid.ApiCallModel

class WorldStatModel : ApiCallModel {
    override val apiURL: String = worldLookupSummaryApiURL

    companion object {
        const val worldLookupSummaryApiURL = "https://api.covid19api.com/summary"
    }
}