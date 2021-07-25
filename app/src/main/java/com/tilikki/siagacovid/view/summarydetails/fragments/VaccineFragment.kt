package com.tilikki.siagacovid.view.summarydetails.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.tilikki.siagacovid.databinding.FragmentCaseDetailVaccineBinding
import com.tilikki.siagacovid.utils.ViewUtility
import com.tilikki.siagacovid.view.summarydetails.CaseSummaryDetailViewModel

class VaccineFragment : BaseCaseDetailFragment() {

    private lateinit var vaccineTestingViewModel: VaccineTestingViewModel
    private lateinit var binding: FragmentCaseDetailVaccineBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootViewModel = ViewModelProvider(requireActivity())
            .get(CaseSummaryDetailViewModel::class.java)
        vaccineTestingViewModel =
            ViewModelProvider(this).get(VaccineTestingViewModel::class.java)

        binding = FragmentCaseDetailVaccineBinding.inflate(inflater, container, false)
        startObserve()
        return binding.root
    }

    override fun setupViewModelObserver() {
        rootViewModel.vaccinationOverview.observe(viewLifecycleOwner, {
            binding.apply {
                ViewUtility.setStatisticPairs(it.firstDose, tvFirstDose, tvDailyFirstDose)
                ViewUtility.setStatisticPairs(it.secondDose, tvSecondDose, tvDailySecondDose)
            }
        })
    }

    override fun toggleFetchState(isFetching: Boolean) {
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
        private const val VACCINATION_DATA = "VACCINATION_DATA"
    }
}
