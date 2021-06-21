package com.tilikki.bnccapp.siagacovid.hotline

import com.squareup.moshi.Json

data class HotlineData(
    @Json(name = "img_icon") val imgIcon: String,
    @Json(name = "name") val name: String,
    @Json(name = "phone") val phone: String
)
