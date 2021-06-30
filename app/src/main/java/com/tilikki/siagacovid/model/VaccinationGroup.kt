package com.tilikki.siagacovid.model

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.tilikki.siagacovid.R

enum class VaccinationGroup(@StringRes val groupName: Int, @ColorRes val color: Int) {
    MEDICAL(R.string.medical, R.color.red),
    PUBLIC_SERVICE(R.string.public_service, R.color.green),
    ELDERS(R.string.elders, R.color.yellow),
    GENERAL_PERSON(R.string.general_public, R.color.teal)
}
