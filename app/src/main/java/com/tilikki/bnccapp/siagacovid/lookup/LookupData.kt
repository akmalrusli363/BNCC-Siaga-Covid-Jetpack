package com.tilikki.bnccapp.siagacovid.lookup

data class LookupData(
    val provinceName: String,
    var numOfPositiveCase: Int = 0,
    var numOfRecoveredCase: Int = 0,
    var numOfDeathCase: Int = 0,
    val provinceID: Int = -1
)