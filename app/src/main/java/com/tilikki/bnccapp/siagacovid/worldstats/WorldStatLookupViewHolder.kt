package com.tilikki.bnccapp.siagacovid.worldstats

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tilikki.bnccapp.siagacovid.utils.FlagUtils
import kotlinx.android.synthetic.main.item_country_lookup.view.*

class WorldStatLookupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(lookupData: WorldStatLookupData){
        itemView.tvLookupCountry.text = lookupData.countryName
        itemView.tvLookupCountryFlag.text = getCountryFlag(lookupData.countryCode)

        itemView.tvLookupConfirmedCase.text = lookupData.numOfConfirmedCase.toString()
        itemView.tvLookupRecoveredCase.text = lookupData.numOfRecoveredCase.toString()
        itemView.tvLookupDeathCase.text = lookupData.numOfDeathCase.toString()

        itemView.tvLookupDailyConfirmedCase.text = getDailyCases(lookupData.numOfDailyConfirmedCase)
        itemView.tvLookupDailyRecoveredCase.text = getDailyCases(lookupData.numOfDailyRecoveredCase)
        itemView.tvLookupDailyDeathCase.text = getDailyCases(lookupData.numOfDailyDeathCase)

        itemView.tvLookupConfirmedCaseDesc.text = getActiveCaseData(lookupData)
        itemView.tvLookupRecoveredCaseDesc.text = getPercentageData((lookupData.numOfRecoveredCase/lookupData.numOfConfirmedCase.toDouble()))
        itemView.tvLookupDeathCaseDesc.text = getPercentageData((lookupData.numOfDeathCase/lookupData.numOfConfirmedCase.toDouble()))
    }

    private fun getCountryFlag(countryCode: String): String {
        return FlagUtils.printFlagEmoji(countryCode)
    }

    private fun getActiveCaseData(lookupData: WorldStatLookupData): String {
        val active = lookupData.numOfConfirmedCase - (lookupData.numOfRecoveredCase + lookupData.numOfDeathCase)
        val activePercentage = active / lookupData.numOfConfirmedCase.toDouble()
        return "(${formatPercentage(activePercentage)}% active)"
    }

    private fun getPercentageData(percentage: Double): String {
        return "(${formatPercentage(percentage)}% cases)"
    }

    private fun getDailyCases(caseCounts: Int): String {
        return "(+${caseCounts})"
    }

    private fun formatPercentage(percentage: Double) = "%.${2}f".format((percentage * 100))
}