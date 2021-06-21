package com.tilikki.bnccapp.siagacovid.lookup

import androidx.recyclerview.widget.RecyclerView
import com.tilikki.bnccapp.databinding.ItemLookupBinding

class LookupViewHolder(private val itemLookupBinding: ItemLookupBinding) :
    RecyclerView.ViewHolder(itemLookupBinding.root) {

    fun bind(data: LookupData) {
        itemLookupBinding.run {
            tvLookupProvince.text = data.provinceName
            tvLookupConfirmedCase.text = data.numOfPositiveCase.toString()
            tvLookupRecoveredCase.text = data.numOfRecoveredCase.toString()
            tvLookupDeathCase.text = data.numOfDeathCase.toString()
            tvLookupDailyConfirmedCase.text = displayDailyCaseCount(data.numOfDailyPositiveCase)
            tvLookupDailyRecoveredCase.text = displayDailyCaseCount(data.numOfDailyRecoveredCase)
            tvLookupDailyDeathCase.text = displayDailyCaseCount(data.numOfDailyDeathCase)
        }
    }

    private fun displayDailyCaseCount(dailyCaseCount: Int): String = "(+${dailyCaseCount})"
}
