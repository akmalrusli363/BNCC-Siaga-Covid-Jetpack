package com.tilikki.bnccapp.siagacovid.view.worldstats

import com.tilikki.bnccapp.siagacovid.model.CountryLookupData

object WorldStatDataComparator {
    val COMPARE_BY_COUNTRY_CODE: Comparator<CountryLookupData> =
        Comparator { a, b -> a.countryCode.compareTo(b.countryCode) }

    val COMPARE_BY_COUNTRY_NAME: Comparator<CountryLookupData> =
        Comparator { a, b -> a.countryName.compareTo(b.countryName) }

    val COMPARE_BY_POSITIVITY_RATE: Comparator<CountryLookupData> =
        Comparator { a, b -> b.confirmedCase.total - a.confirmedCase.total }

    val COMPARE_BY_DAILY_POSITIVITY_RATE: Comparator<CountryLookupData> =
        Comparator { a, b -> b.confirmedCase.added - a.confirmedCase.added }

    val COMPARE_BY_RECOVERY_RATE: Comparator<CountryLookupData> =
        Comparator { a, b -> b.recoveredCase.total - a.recoveredCase.total }

    val COMPARE_BY_DAILY_RECOVERY_RATE: Comparator<CountryLookupData> =
        Comparator { a, b -> b.recoveredCase.added - a.recoveredCase.added }

    val COMPARE_BY_DEATH_RATE: Comparator<CountryLookupData> =
        Comparator { a, b -> b.deathCase.total - a.deathCase.total }

    val COMPARE_BY_DAILY_DEATH_RATE: Comparator<CountryLookupData> =
        Comparator { a, b -> b.deathCase.added - a.deathCase.added }
}
