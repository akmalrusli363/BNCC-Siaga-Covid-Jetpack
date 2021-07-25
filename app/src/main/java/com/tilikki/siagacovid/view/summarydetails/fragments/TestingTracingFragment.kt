package com.tilikki.siagacovid.view.summarydetails.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.tilikki.siagacovid.databinding.FragmentCaseDetailTestingTracingBinding
import com.tilikki.siagacovid.utils.ViewUtility
import com.tilikki.siagacovid.view.summarydetails.CaseSummaryDetailViewModel

class TestingTracingFragment : BaseCaseDetailFragment() {

    private lateinit var vaccineTestingViewModel: VaccineTestingViewModel
    private lateinit var binding: FragmentCaseDetailTestingTracingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootViewModel = ViewModelProvider(requireActivity())
            .get(CaseSummaryDetailViewModel::class.java)
        vaccineTestingViewModel =
            ViewModelProvider(this).get(VaccineTestingViewModel::class.java)

        binding = FragmentCaseDetailTestingTracingBinding.inflate(inflater, container, false)
        startObserve()
        return binding.root
    }

    override fun setupViewModelObserver() {
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
    }

    override fun toggleFetchState(isFetching: Boolean) {
        binding.apply {
            toggleViewFetchState(
                isFetching, listOf(tvAntigenSample, tvDailyAntigenSample), pbAntigenSample
            )
            toggleViewFetchState(
                isFetching, listOf(tvPeopleAntigenTested, tvDailyPeopleAntigenTested),
                pbPeopleAntigenTested
            )
            toggleViewFetchState(
                isFetching, listOf(tvPCRSample, tvDailyPCRSample), pbPCRSample
            )
            toggleViewFetchState(
                isFetching, listOf(tvPeoplePCRTested, tvDailyPeoplePCRTested), pbPeoplePCRTested
            )
            toggleViewFetchState(
                isFetching, listOf(tvTestSample, tvDailyTestSample), pbTestSample
            )
            toggleViewFetchState(
                isFetching, listOf(tvPeopleTested, tvDailyPeopleTested), pbPeopleTested
            )
        }
    }

    companion object {
        private const val COVID_TESTING_DATA = "COVID_TESTING_DATA"
    }
}
