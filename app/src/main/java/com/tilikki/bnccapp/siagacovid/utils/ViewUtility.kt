package com.tilikki.bnccapp.siagacovid.utils

import android.view.View
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

    private fun displayDailyCaseCount(dailyCaseCount: Int): String {
        if (dailyCaseCount == Int.MIN_VALUE) {
            return ""
        }
        val sign = when {
            dailyCaseCount < 0 -> '-'
            else -> '+'
        }
        return "(${sign}${dailyCaseCount.absoluteValue})"
    }

    fun setVisibility(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }
}
