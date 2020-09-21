package com.tilikki.bnccapp.siagacovid.lookup

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_lookup.view.*
import java.util.*

class LookupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(data: LookupData){
        itemView.tvLookUpProvince.text = data.provinceName
        itemView.tvLookUpConfirmedCase.text = data.numOfPositiveCase.toString()
        itemView.tvLookUpRecoveredCase.text = data.numOfRecoveredCase.toString()
        itemView.tvLookUpDeathCase.text = data.numOfDeathCase.toString()
        itemView.tvLookUpDailyConfirmedCase.text = displayDailyCaseCount(data.numOfDailyPositiveCase)
        itemView.tvLookUpDailyRecoveredCase.text = displayDailyCaseCount(data.numOfDailyRecoveredCase)
        itemView.tvLookUpDailyDeathCase.text = displayDailyCaseCount(data.numOfDailyDeathCase)
    }

    private fun displayDailyCaseCount(dailyCaseCount: Int): String = "(+${dailyCaseCount})"
}