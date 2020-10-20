package com.tilikki.bnccapp.siagacovid.worldstats

object WorldStatDataComparator {
    val COMPARE_BY_COUNTRY_CODE: Comparator<WorldStatLookupData> =
        Comparator<WorldStatLookupData> { a, b -> a.countryCode.compareTo(b.countryCode) }

    val COMPARE_BY_COUNTRY_NAME: Comparator<WorldStatLookupData> =
        Comparator<WorldStatLookupData> { a, b -> a.countryName.compareTo(b.countryName) }

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
}