package com.tilikki.siagacovid.model

import java.util.*

data class CovidTestingData(
    val updated: Date,
    val antigen: CovidTestMethod,
    val pcr: CovidTestMethod
) {
    data class CovidTestMethod(
        val samples: CountStatistics,
        val peopleTested: CountStatistics
    ) {
        operator fun plus(operand: CovidTestMethod): CovidTestMethod {
            return CovidTestMethod(
                samples = this.samples + operand.samples,
                peopleTested = this.peopleTested + operand.peopleTested
            )
        }
    }

    val overall: CovidTestMethod = antigen + pcr
}
