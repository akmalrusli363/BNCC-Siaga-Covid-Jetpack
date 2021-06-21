package com.tilikki.bnccapp.siagacovid.lookup

import androidx.recyclerview.widget.RecyclerView
import com.tilikki.bnccapp.databinding.ItemLookupBinding
import com.tilikki.bnccapp.siagacovid.lookup.netmodel.RegionData

class LookupViewHolder(private val itemLookupBinding: ItemLookupBinding) :
    RecyclerView.ViewHolder(itemLookupBinding.root) {

    fun bind(data: RegionData) {
        itemLookupBinding.run {
            tvLookupProvince.text = data.province
            tvLookupConfirmedCase.text = data.totalConfirmedCase.toString()
            tvLookupRecoveredCase.text = data.totalRecoveredCase.toString()
            tvLookupDeathCase.text = data.totalDeathCase.toString()
            tvLookupDailyConfirmedCase.text = displayDailyCaseCount(data.dailyCase.confirmedCase)
            tvLookupDailyRecoveredCase.text = displayDailyCaseCount(data.dailyCase.recoveredCase)
            tvLookupDailyDeathCase.text = displayDailyCaseCount(data.dailyCase.deathCase)
        }
    }

    private fun displayDailyCaseCount(dailyCaseCount: Int): String = "(+${dailyCaseCount})"
}
