package com.tilikki.bnccapp.siagacovid.worldstats

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tilikki.bnccapp.siagacovid.utils.FlagUtils
import kotlinx.android.synthetic.main.item_country_lookup.view.*

class WorldStatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(data: WorldStatData){
        itemView.tvLookupCountry.text = data.countryName
        itemView.tvLookupCountryFlag.text = getCountryFlag(data.countryCode)

        itemView.tvLookupConfirmedCase.text = data.numOfConfirmedCase.toString()
        itemView.tvLookupRecoveredCase.text = data.numOfRecoveredCase.toString()
        itemView.tvLookupDeathCase.text = data.numOfDeathCase.toString()

        itemView.tvLookupDailyConfirmedCase.text = getDailyCases(data.numOfDailyConfirmedCase)
        itemView.tvLookupDailyRecoveredCase.text = getDailyCases(data.numOfDailyRecoveredCase)
        itemView.tvLookupDailyDeathCase.text = getDailyCases(data.numOfDailyDeathCase)

        itemView.tvLookupConfirmedCaseDesc.text = getActiveCaseData(data)
        itemView.tvLookupRecoveredCaseDesc.text = getPercentageData((data.numOfRecoveredCase/data.numOfConfirmedCase.toDouble()))
        itemView.tvLookupDeathCaseDesc.text = getPercentageData((data.numOfDeathCase/data.numOfConfirmedCase.toDouble()))
    }

    private fun getCountryFlag(countryCode: String): String {
        return FlagUtils.printFlagEmoji(countryCode)
    }

    private fun getActiveCaseData(data: WorldStatData): String {
        val active = data.numOfConfirmedCase - data.numOfRecoveredCase
        val activePercentage = active / data.numOfConfirmedCase.toDouble()
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