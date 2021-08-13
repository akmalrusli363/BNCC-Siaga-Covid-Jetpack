package com.tilikki.siagacovid.view.summarydetails.netmodel.casehistory

import com.squareup.moshi.Json

data class CaseHistoryRootData(
    @Json(name = "update") val update: CaseHistoryData
)
