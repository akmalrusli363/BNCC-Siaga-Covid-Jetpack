package com.tilikki.bnccapp.siagacovid.lookup

import androidx.recyclerview.widget.RecyclerView
import com.tilikki.bnccapp.databinding.ItemLookupBinding
import com.tilikki.bnccapp.siagacovid.model.RegionLookupData
import com.tilikki.bnccapp.siagacovid.utils.ViewUtility

class LookupViewHolder(private val itemLookupBinding: ItemLookupBinding) :
    RecyclerView.ViewHolder(itemLookupBinding.root) {

    fun bind(data: RegionLookupData) {
        itemLookupBinding.run {
            tvLookupProvince.text = data.province
            ViewUtility.run {
                setStatisticPairs(
                    data.confirmedCase,
                    tvLookupConfirmedCase,
                    tvLookupDailyConfirmedCase
                )
                setStatisticPairs(
                    data.recoveredCase,
                    tvLookupRecoveredCase,
                    tvLookupDailyRecoveredCase
                )
                setStatisticPairs(data.deathCase, tvLookupDeathCase, tvLookupDailyDeathCase)
            }
        }
    }
}
