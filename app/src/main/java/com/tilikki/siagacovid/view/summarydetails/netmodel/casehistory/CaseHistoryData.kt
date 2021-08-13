package com.tilikki.siagacovid.view.summarydetails.netmodel.casehistory

import com.squareup.moshi.Json
import com.tilikki.siagacovid.model.CaseOverview
import com.tilikki.siagacovid.view.summarydetails.netmodel.chronology.ChronologicalCase

data class CaseHistoryData(
    @Json(name = "harian") val history: List<ChronologicalCase>,
) {
    fun toHistoricalCaseOverview(): List<CaseOverview> {
        return history.map {
            it.toCaseOverview()
        }
    }
}
