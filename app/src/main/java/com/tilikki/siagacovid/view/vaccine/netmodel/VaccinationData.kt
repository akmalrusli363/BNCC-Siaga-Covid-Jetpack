package com.tilikki.siagacovid.view.vaccine.netmodel

import com.squareup.moshi.Json
import com.tilikki.siagacovid.model.VaccinationDetail
import com.tilikki.siagacovid.model.VaccinationGroup
import com.tilikki.siagacovid.model.VaccinationOverview
import java.util.*

data class VaccinationData(
    @Json(name = "date") val date: Date,
    @Json(name = "total_sasaran_vaksinasi") val targetVaccination: Int,
    @Json(name = "sasaran_vaksinasi_sdmk") val medicalGroupTarget: Int,
    @Json(name = "sasaran_vaksinasi_petugas_publik") val publicServiceGroupTarget: Int,
    @Json(name = "sasaran_vaksinasi_lansia") val elderGroupTarget: Int,
    @Json(name = "sasaran_vaksinasi_masyarakat_umum") val generalGroupTarget: Int?,
    @Json(name = "sasaran_vaksinasi_kelompok_1217") val teenagerGroupTarget: Int?,
    @Json(name = "vaksinasi1") val firstDose: Int,
    @Json(name = "vaksinasi2") val secondDose: Int,
    @Json(name = "tahapan_vaksinasi") val vaccinationStage: VaccinationStage,
    @Json(name = "cakupan") val coverage: VaccinationCoverage
) {
    data class VaccinationStage(
        @Json(name= "sdm_kesehatan") val medical: VaccinationStageDetail,
        @Json(name= "petugas_publik") val publicService: VaccinationStageDetail,
        @Json(name= "lansia") val elders: VaccinationStageDetail,
        @Json(name= "masyarakat_umum") val generalGroup: VaccinationStageDetail?,
        @Json(name= "kelompok_usia_12_17") val teenagers: VaccinationStageDetail?,
    )

    data class VaccinationStageDetail(
        @Json(name = "total_vaksinasi1") val firstDose: Int,
        @Json(name = "total_vaksinasi2") val secondDose: Int
    )

    data class VaccinationCoverage(
        @Json(name = "vaksinasi1") val overallFirstDose: String,
        @Json(name = "vaksinasi2") val overallSecondDose: String,
        @Json(name = "sdm_kesehatan_vaksinasi1") val medicalFirstDose: String,
        @Json(name = "sdm_kesehatan_vaksinasi2") val medicalSecondDose: String,
        @Json(name = "petugas_publik_vaksinasi1") val publicServiceFirstDose: String,
        @Json(name = "petugas_publik_vaksinasi2") val publicServiceSecondDose: String,
        @Json(name = "lansia_vaksinasi1") val eldersFirstDose: String,
        @Json(name = "lansia_vaksinasi2") val eldersSecondDose: String,
        @Json(name = "masyarakat_umum_vaksinasi1") val generalGroupFirstDose: String?,
        @Json(name = "masyarakat_umum_vaksinasi2") val generalGroupSecondDose: String?,
        @Json(name = "kelompok_usia_12_17_vaksinasi1") val teenagersFirstDose: String?,
        @Json(name = "kelompok_usia_12_17_vaksinasi2") val teenagersSecondDose: String?,
    )

    fun toVaccinationOverview(): VaccinationOverview {
        val vaccinationStages: MutableMap<VaccinationGroup, VaccinationDetail> = mutableMapOf(
            Pair(VaccinationGroup.MEDICAL, VaccinationDetail(
                group = VaccinationGroup.MEDICAL,
                target = medicalGroupTarget,
                firstDose = vaccinationStage.medical.firstDose,
                secondDose = vaccinationStage.medical.secondDose,
                firstDosePercentage = coverage.medicalFirstDose,
                secondDosePercentage = coverage.medicalSecondDose,
            )),
            Pair(VaccinationGroup.PUBLIC_SERVICE, VaccinationDetail(
                group = VaccinationGroup.PUBLIC_SERVICE,
                target = publicServiceGroupTarget,
                firstDose = vaccinationStage.publicService.firstDose,
                secondDose = vaccinationStage.publicService.secondDose,
                firstDosePercentage = coverage.publicServiceFirstDose,
                secondDosePercentage = coverage.publicServiceSecondDose,
            )),
            Pair(VaccinationGroup.ELDERS, VaccinationDetail(
                group = VaccinationGroup.ELDERS,
                target = elderGroupTarget,
                firstDose = vaccinationStage.elders.firstDose,
                secondDose = vaccinationStage.elders.secondDose,
                firstDosePercentage = coverage.eldersFirstDose,
                secondDosePercentage = coverage.eldersSecondDose,
            )),
        ).apply {
            vaccinationStage.generalGroup?.let {
                put(VaccinationGroup.GENERAL_GROUP, VaccinationDetail(
                    group = VaccinationGroup.GENERAL_GROUP,
                    target = generalGroupTarget!!,
                    firstDose = vaccinationStage.generalGroup.firstDose,
                    secondDose = vaccinationStage.generalGroup.secondDose,
                    firstDosePercentage = coverage.generalGroupFirstDose!!,
                    secondDosePercentage = coverage.generalGroupSecondDose!!,
                ))
            }
            vaccinationStage.teenagers?.let {
                put(VaccinationGroup.TEENAGERS, VaccinationDetail(
                    group = VaccinationGroup.TEENAGERS,
                    target = teenagerGroupTarget!!,
                    firstDose = vaccinationStage.teenagers.firstDose,
                    secondDose = vaccinationStage.teenagers.secondDose,
                    firstDosePercentage = coverage.teenagersFirstDose!!,
                    secondDosePercentage = coverage.teenagersSecondDose!!,
                ))
            }
        }
        return VaccinationOverview(
            date = date,
            targetVaccination = targetVaccination,
            firstDose = firstDose,
            secondDose = secondDose,
            firstDosePercentage = coverage.overallFirstDose,
            secondDosePercentage = coverage.overallSecondDose,
            vaccinationStages = vaccinationStages
        )
    }
}
