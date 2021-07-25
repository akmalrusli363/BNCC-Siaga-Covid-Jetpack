package com.tilikki.siagacovid.view.summarydetails.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.tilikki.siagacovid.databinding.FragmentCaseDetailHomeBinding
import com.tilikki.siagacovid.utils.ViewUtility
import com.tilikki.siagacovid.view.summarydetails.CaseSummaryDetailViewModel

class CaseOverviewFragment : BaseCaseDetailFragment() {

    private lateinit var binding: FragmentCaseDetailHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootViewModel = ViewModelProvider(requireActivity())
            .get(CaseSummaryDetailViewModel::class.java)

        binding = FragmentCaseDetailHomeBinding.inflate(inflater, container, false)
        startObserve()
        return binding.root
    }

    override fun setupViewModelObserver() {
        rootViewModel.caseOverview.observe(viewLifecycleOwner, {
            binding.apply {
                ViewUtility.run {
                    setStatisticPairs(it.confirmedCase, tvTotalCaseCount, tvDailyConfirmedCaseCount)
                    setStatisticPairs(it.activeCase, tvPositiveCount, tvDailyPositiveCount)
                    setStatisticPairs(it.recoveredCase, tvRecoveredCount, tvDailyRecoveredCount)
                    setStatisticPairs(it.deathCase, tvDeathCount, tvDailyDeathCount)
                }
            }
        })
        rootViewModel.testTracing.observe(viewLifecycleOwner, {
            binding.apply {
                ViewUtility.setStatisticPairs(
                    it.antigen.samples, tvAntigenSample, tvDailyAntigenSample
                )
                ViewUtility.setStatisticPairs(
                    it.antigen.peopleTested, tvPeopleAntigenTested, tvDailyPeopleAntigenTested
                )
                ViewUtility.setStatisticPairs(it.pcr.samples, tvPCRSample, tvDailyPCRSample)
                ViewUtility.setStatisticPairs(
                    it.pcr.peopleTested, tvPeoplePCRTested, tvDailyPeoplePCRTested
                )
                ViewUtility.setStatisticPairs(it.overall.samples, tvTestSample, tvDailyTestSample)
                ViewUtility.setStatisticPairs(
                    it.overall.peopleTested, tvPeopleTested, tvDailyPeopleTested
                )
            }
        })
        rootViewModel.vaccinationOverview.observe(viewLifecycleOwner, {
            binding.apply {
                ViewUtility.setStatisticPairs(it.firstDose, tvFirstDose, tvDailyFirstDose)
                ViewUtility.setStatisticPairs(it.secondDose, tvSecondDose, tvDailySecondDose)
            }
        })
    }

    override fun toggleFetchState(isFetching: Boolean) {
        toggleCaseFetchState(isFetching)
        toggleTestingVaccinationFetchState(isFetching)
    }

    private fun toggleCaseFetchState(isFetching: Boolean) {
        binding.apply {
            toggleViewFetchState(
                isFetching, listOf(tvTotalCaseCount, tvDailyConfirmedCaseCount), pbTotalCase
            )
            toggleViewFetchState(
                isFetching, listOf(tvPositiveCount, tvDailyPositiveCount), pbPositive
            )
            toggleViewFetchState(
                isFetching, listOf(tvRecoveredCount, tvDailyRecoveredCount), pbRecovered
            )
            toggleViewFetchState(isFetching, listOf(tvDeathCount, tvDailyDeathCount), pbDeath)
        }
    }

    private fun toggleTestingVaccinationFetchState(isFetching: Boolean) {
        binding.apply {
            toggleViewFetchState(
                isFetching, listOf(tvFirstDose, tvDailyFirstDose), pbFirstDose
            )
            toggleViewFetchState(
                isFetching, listOf(tvSecondDose, tvDailySecondDose), pbSecondDose
            )
        }
    }

    companion object {
        private const val CASE_OVERVIEW_DATA = "CASE_OVERVIEW_DATA"
    }
}
