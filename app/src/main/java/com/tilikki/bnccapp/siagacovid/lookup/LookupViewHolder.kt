package com.tilikki.bnccapp.siagacovid.lookup

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_lookup.view.*

class LookupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(data: LookupData) {
        itemView.tvLookupProvince.text = data.provinceName
        itemView.tvLookupConfirmedCase.text = data.numOfPositiveCase.toString()
        itemView.tvLookupRecoveredCase.text = data.numOfRecoveredCase.toString()
        itemView.tvLookupDeathCase.text = data.numOfDeathCase.toString()
        itemView.tvLookupDailyConfirmedCase.text =
            displayDailyCaseCount(data.numOfDailyPositiveCase)
        itemView.tvLookupDailyRecoveredCase.text =
            displayDailyCaseCount(data.numOfDailyRecoveredCase)
        itemView.tvLookupDailyDeathCase.text = displayDailyCaseCount(data.numOfDailyDeathCase)
    }

    private fun displayDailyCaseCount(dailyCaseCount: Int): String =
        if (dailyCaseCount < 0) "" else "(+${dailyCaseCount})"
}
