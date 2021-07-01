package com.tilikki.siagacovid.lookup

import androidx.recyclerview.widget.RecyclerView
import com.tilikki.siagacovid.databinding.ItemLookupBinding
import com.tilikki.siagacovid.model.RegionLookupData
import com.tilikki.siagacovid.utils.ViewUtility

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
