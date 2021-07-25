package com.tilikki.siagacovid.view.summarydetails.fragments

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.tilikki.siagacovid.utils.AppEventLogging
import com.tilikki.siagacovid.utils.ViewUtility
import com.tilikki.siagacovid.view.summarydetails.CaseSummaryDetailViewModel

abstract class BaseCaseDetailFragment: Fragment() {

    protected lateinit var rootViewModel: CaseSummaryDetailViewModel

    fun startObserve() {
        toggleFetchState(true)
        setupViewModelObserver()
        rootViewModel.successResponse.observe(viewLifecycleOwner, {
            if (!it.success && it.error != null) {
                showError(AppEventLogging.FETCH_FAILURE, it.error as Exception)
            } else {
                toggleFetchState(false)
            }
        })
    }

    abstract fun setupViewModelObserver()

    abstract fun toggleFetchState(isFetching: Boolean)

    protected fun toggleViewFetchState(
        isFetching: Boolean,
        views: List<View>,
        progressBar: ProgressBar
    ) {
        ViewUtility.setVisibility(progressBar, isFetching)
        for (view in views) {
            ViewUtility.setVisibility(view, !isFetching)
        }
    }

    protected fun showError(tag: String, e: Exception) {
        Log.e(tag, e.message, e)
        AppEventLogging.logExceptionOnToast(tag, requireActivity(), e)
    }

}
