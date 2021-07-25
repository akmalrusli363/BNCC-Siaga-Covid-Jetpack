package com.tilikki.siagacovid.model

data class CountStatistics(
    val total: Int, val added: Int
) {
    constructor(total: Int, added: Int?) : this(total, added ?: Int.MIN_VALUE)

    operator fun plus(other: CountStatistics): CountStatistics {
        return CountStatistics(
            total = this.total + other.total,
            added = this.added + other.added
        )
    }
}
