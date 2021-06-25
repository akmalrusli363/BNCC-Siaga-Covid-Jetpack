package com.tilikki.bnccapp.siagacovid.worldstats

import androidx.recyclerview.widget.RecyclerView
import com.tilikki.bnccapp.databinding.ItemCountryLookupBinding
import com.tilikki.bnccapp.siagacovid.model.CountryLookupData
import com.tilikki.bnccapp.siagacovid.utils.FlagUtils
import com.tilikki.bnccapp.siagacovid.utils.ViewUtility

class WorldStatLookupViewHolder(private val binding: ItemCountryLookupBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val caseLabel = "cases"
    private val activeLabel = "active"

    fun bind(lookupData: CountryLookupData) {
        binding.run {
            tvLookupCountry.text = lookupData.countryName
            tvLookupCountryFlag.text = getCountryFlag(lookupData.countryCode)

            ViewUtility.setStatisticPairs(
                lookupData.confirmedCase,
                tvLookupConfirmedCase,
                tvLookupDailyConfirmedCase
            )
            ViewUtility.setStatisticPairs(
                lookupData.recoveredCase,
                tvLookupRecoveredCase,
                tvLookupDailyRecoveredCase
            )
            ViewUtility.setStatisticPairs(
                lookupData.deathCase,
                tvLookupDeathCase,
                tvLookupDailyDeathCase
            )
            tvLookupConfirmedCaseDesc.text =
                getPercentageData(lookupData.positivityRate(), activeLabel)
            tvLookupRecoveredCaseDesc.text =
                getPercentageData(lookupData.recoveryRate(), caseLabel)
            tvLookupDeathCaseDesc.text =
                getPercentageData(lookupData.deathRate(), caseLabel)
        }
    }

    private fun getCountryFlag(countryCode: String): String {
        return FlagUtils.printFlagEmoji(countryCode)
    }

    private fun getPercentageData(percentage: Double, label: String?): String {
        return if (!label.isNullOrBlank()) "(${formatPercentage(percentage)}% $label)"
        else "(${formatPercentage(percentage)}%)"
    }

    private fun formatPercentage(percentage: Double) = "%.${2}f".format((percentage * 100))
}
