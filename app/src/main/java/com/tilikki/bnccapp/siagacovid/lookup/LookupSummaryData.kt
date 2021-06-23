package com.tilikki.bnccapp.siagacovid.lookup

import com.tilikki.bnccapp.siagacovid.model.RegionLookupData
import java.util.*

data class LookupSummaryData(
    var lookupData: MutableList<RegionLookupData>,
    val provider: String,
    val lastUpdated: Date?
)
