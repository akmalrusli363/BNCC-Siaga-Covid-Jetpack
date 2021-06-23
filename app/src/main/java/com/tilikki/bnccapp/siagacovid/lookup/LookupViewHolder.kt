package com.tilikki.bnccapp.siagacovid.lookup

import androidx.recyclerview.widget.RecyclerView
import com.tilikki.bnccapp.databinding.ItemLookupBinding
import com.tilikki.bnccapp.siagacovid.model.RegionLookupData
import com.tilikki.bnccapp.siagacovid.utils.ViewUtility

class LookupViewHolder(val binding: ItemLookupBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(data: RegionLookupData) {
        binding.run {
            tvLookupProvince.text = data.province
            ViewUtility.setStatisticPairs(
                data.confirmedCase,
                tvLookupConfirmedCase,
                tvLookupDailyConfirmedCase
            )
            ViewUtility.setStatisticPairs(
                data.recoveredCase,
                tvLookupRecoveredCase,
                tvLookupDailyRecoveredCase
            )
            ViewUtility.setStatisticPairs(data.deathCase, tvLookupDeathCase, tvLookupDailyDeathCase)
        }
    }
}
