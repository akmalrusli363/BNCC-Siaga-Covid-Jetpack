package com.tilikki.siagacovid.model

data class CountStatistics(
    val total: Int, val added: Int
) {
    constructor(total: Int, added: Int?) : this(total, added ?: Int.MIN_VALUE)
}
