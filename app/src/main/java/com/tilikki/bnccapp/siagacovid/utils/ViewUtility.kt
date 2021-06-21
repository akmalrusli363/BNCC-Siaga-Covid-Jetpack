package com.tilikki.bnccapp.siagacovid.utils

import android.widget.TextView
import com.tilikki.bnccapp.siagacovid.model.CountStatistics
import kotlin.math.absoluteValue

object ViewUtility {
    fun setStatisticPairs(
        countStatistics: CountStatistics,
        tvCumulative: TextView,
        tvDaily: TextView
    ) {
        tvCumulative.text = "${countStatistics.total}"
        tvDaily.text = displayDailyCaseCount(countStatistics.added)
    }

    fun displayDailyCaseCount(dailyCaseCount: Int): String {
        val sign = when {
            dailyCaseCount < 0 -> '-'
            else -> '+'
        }
        return "(${sign}${dailyCaseCount.absoluteValue})"
    }
}
