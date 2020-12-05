package com.tilikki.bnccapp.siagacovid.lookup

import java.util.*

data class LookupSummaryData(
    var lookupData: MutableList<LookupData>,
    val provider: String,
    val lastUpdated: Date?
)