package com.tilikki.siagacovid.view.summarydetails.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tilikki.siagacovid.databinding.FragmentCaseDetailCasesBinding
import com.tilikki.siagacovid.utils.AppEventLogging
import com.tilikki.siagacovid.utils.ViewUtility
import com.tilikki.siagacovid.view.summarydetails.CaseSummaryDetailViewModel

class CaseDataFragment : Fragment() {

    private lateinit var rootViewModel: CaseSummaryDetailViewModel
    private lateinit var binding: FragmentCaseDetailCasesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootViewModel = ViewModelProvider(requireActivity())
            .get(CaseSummaryDetailViewModel::class.java)

        binding = FragmentCaseDetailCasesBinding.inflate(inflater, container, false)
        startObserve()

        return binding.root
    }

    private fun startObserve() {
        toggleFetchState(true)
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
        rootViewModel.successResponse.observe(viewLifecycleOwner, {
            if (!it.success && it.error != null) {
                showError(AppEventLogging.FETCH_FAILURE, it.error as Exception)
            } else {
                toggleFetchState(false)
            }
        })
    }

    private fun toggleFetchState(isFetching: Boolean) {
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

    private fun toggleViewFetchState(
        isFetching: Boolean,
        views: List<View>,
        progressBar: ProgressBar
    ) {
        ViewUtility.setVisibility(progressBar, isFetching)
        for (view in views) {
            ViewUtility.setVisibility(view, !isFetching)
        }
    }

    private fun showError(tag: String, e: Exception) {
        Log.e(tag, e.message, e)
        AppEventLogging.logExceptionOnToast(tag, requireActivity(), e)
    }

    companion object {
        private const val COVID_CASE_DATA_DETAIL = "COVID_CASE_DATA_DETAIL"
    }
}
