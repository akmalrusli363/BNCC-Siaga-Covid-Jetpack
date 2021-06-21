package com.tilikki.bnccapp.siagacovid.repository

import com.tilikki.bnccapp.siagacovid.hotline.HotlineData
import retrofit2.Response

interface CovidHotlineRepository {
    fun getHotline(): Response<List<HotlineData>>
}
