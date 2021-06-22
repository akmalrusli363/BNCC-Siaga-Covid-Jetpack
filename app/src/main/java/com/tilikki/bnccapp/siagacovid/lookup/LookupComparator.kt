package com.tilikki.bnccapp.siagacovid.lookup

import com.tilikki.bnccapp.siagacovid.model.RegionLookupData

class LookupComparator(private val orderLabel: String, val comparator: Comparator<RegionLookupData>) {
    override fun equals(other: Any?): Boolean {
        return comparator.hashCode() == other.hashCode()
    }

    override fun hashCode(): Int {
        return comparator.hashCode()
    }

    override fun toString(): String {
        return orderLabel
    }

    companion object {
        val compareByPositivityRate: Comparator<RegionLookupData> =
            Comparator<RegionLookupData> { a, b -> b.confirmedCase.total - a.confirmedCase.total }

        val compareBySterilityRate: Comparator<RegionLookupData> =
            Comparator<RegionLookupData> { a, b -> a.confirmedCase.total - b.confirmedCase.total }

        val compareByDailyPositivityRate: Comparator<RegionLookupData> =
            Comparator<RegionLookupData> { a, b -> b.confirmedCase.added - a.confirmedCase.added }

        val compareByRecoveryRate: Comparator<RegionLookupData> =
            Comparator<RegionLookupData> { a, b -> b.recoveredCase.total - a.recoveredCase.total }

        val compareByDailyRecoveryRate: Comparator<RegionLookupData> =
            Comparator<RegionLookupData> { a, b -> b.recoveredCase.added - a.recoveredCase.added }

        val compareByDeathRate: Comparator<RegionLookupData> =
            Comparator<RegionLookupData> { a, b -> b.deathCase.total - a.deathCase.total }

        val compareByDailyDeathRate: Comparator<RegionLookupData> =
            Comparator<RegionLookupData> { a, b -> b.deathCase.added - a.deathCase.added }
    }
}
