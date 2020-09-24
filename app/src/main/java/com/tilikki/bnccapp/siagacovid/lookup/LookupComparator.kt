package com.tilikki.bnccapp.siagacovid.lookup;

class LookupComparator(private val orderLabel: String, val comparator: Comparator<LookupData>) {
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
        val compareByPositivityRate: Comparator<LookupData> =
            Comparator<LookupData> { a, b -> b.numOfPositiveCase - a.numOfPositiveCase }

        val compareBySterilityRate: Comparator<LookupData> =
            Comparator<LookupData> { a, b -> a.numOfPositiveCase - b.numOfPositiveCase }

        val compareByDailyPositivityRate: Comparator<LookupData> =
            Comparator<LookupData> { a, b -> b.numOfDailyPositiveCase - a.numOfDailyPositiveCase }

        val compareByRecoveryRate: Comparator<LookupData> =
            Comparator<LookupData> { a, b -> b.numOfRecoveredCase - a.numOfRecoveredCase }

        val compareByDailyRecoveryRate: Comparator<LookupData> =
            Comparator<LookupData> { a, b -> b.numOfDailyRecoveredCase - a.numOfDailyRecoveredCase }

        val compareByDeathRate: Comparator<LookupData> =
            Comparator<LookupData> { a, b -> b.numOfDeathCase - a.numOfDeathCase }

        val compareByDailyDeathRate: Comparator<LookupData> =
            Comparator<LookupData> { a, b -> b.numOfDailyDeathCase - a.numOfDailyDeathCase }
    }
}