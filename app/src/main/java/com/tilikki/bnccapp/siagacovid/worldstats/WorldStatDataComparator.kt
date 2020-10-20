package com.tilikki.bnccapp.siagacovid.worldstats

class WorldStatDataComparator(private val orderLabel: String, val comparator: Comparator<WorldStatLookupData>) {
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
        val COMPARE_BY_COUNTRY_CODE: Comparator<WorldStatLookupData> =
            Comparator<WorldStatLookupData> { a, b -> b.countryCode.compareTo(a.countryCode) }

        val COMPARE_BY_COUNTRY_NAME: Comparator<WorldStatLookupData> =
            Comparator<WorldStatLookupData> { a, b -> b.countryName.compareTo(a.countryName) }

        val COMPARE_BY_POSITIVITY_RATE: Comparator<WorldStatLookupData> =
            Comparator<WorldStatLookupData> { a, b -> b.numOfConfirmedCase - a.numOfConfirmedCase }

        val COMPARE_BY_DAILY_POSITIVITY_RATE: Comparator<WorldStatLookupData> =
            Comparator<WorldStatLookupData> { a, b -> b.numOfDailyConfirmedCase - a.numOfDailyConfirmedCase }

        val COMPARE_BY_RECOVERY_RATE: Comparator<WorldStatLookupData> =
            Comparator<WorldStatLookupData> { a, b -> b.numOfRecoveredCase - a.numOfRecoveredCase }

        val COMPARE_BY_DAILY_RECOVERY_RATE: Comparator<WorldStatLookupData> =
            Comparator<WorldStatLookupData> { a, b -> b.numOfDailyRecoveredCase - a.numOfDailyRecoveredCase }

        val COMPARE_BY_DEATH_RATE: Comparator<WorldStatLookupData> =
            Comparator<WorldStatLookupData> { a, b -> b.numOfDeathCase - a.numOfDeathCase }

        val COMPARE_BY_DAILY_DEATH_RATE: Comparator<WorldStatLookupData> =
            Comparator<WorldStatLookupData> { a, b -> b.numOfDailyDeathCase - a.numOfDailyDeathCase }

        val COMPARE_BY_ACTIVE_PERCENTAGE: Comparator<WorldStatLookupData> =
            Comparator<WorldStatLookupData> { a, b -> b.positivityRate().compareTo(a.positivityRate()) }

        val COMPARE_BY_RECOVERY_PERCENTAGE: Comparator<WorldStatLookupData> =
            Comparator<WorldStatLookupData> { a, b -> b.recoveryRate().compareTo(a.recoveryRate()) }

        val COMPARE_BY_DEATH_PERCENTAGE: Comparator<WorldStatLookupData> =
            Comparator<WorldStatLookupData> { a, b -> b.deathRate().compareTo(a.deathRate()) }
    }
}