package com.tilikki.bnccapp.siagacovid.hotline

import com.tilikki.bnccapp.siagacovid.ApiCallModel

class HotlineModel : ApiCallModel {
    override val apiURL: String = hotlineApiURL

    companion object {
        const val hotlineApiURL = "https://bncc-corona-versus.firebaseio.com/v1/hotlines.json"
    }
}