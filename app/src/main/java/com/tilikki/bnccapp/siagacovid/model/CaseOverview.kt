package com.tilikki.bnccapp.siagacovid.model

import com.tilikki.bnccapp.siagacovid.view.overview.netmodel.OverviewData
import java.util.*

data class CaseOverview(
    val confirmedCase: CountStatistics,
    val activeCase: CountStatistics,
    val recoveredCase: CountStatistics,
    val deathCase: CountStatistics,
    val lastUpdated: Date,
) {
    constructor(totalCase: OverviewData.TotalCase, dailyCase: OverviewData.DailyCase) : this(
        CountStatistics(totalCase.confirmedCase, dailyCase.confirmedCase),
        CountStatistics(totalCase.activeCase, dailyCase.activeCase),
        CountStatistics(totalCase.recoveredCase, dailyCase.recoveredCase),
        CountStatistics(totalCase.deathCase, dailyCase.deathCase),
        dailyCase.lastUpdated
    )
}
