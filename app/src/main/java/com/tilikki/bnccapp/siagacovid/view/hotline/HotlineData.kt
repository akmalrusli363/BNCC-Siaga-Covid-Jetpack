package com.tilikki.bnccapp.siagacovid.view.hotline

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HotlineData(
    @Json(name = "img_icon") val imgIcon: String,
    @Json(name = "name") val name: String,
    @Json(name = "phone") val phone: String
)
