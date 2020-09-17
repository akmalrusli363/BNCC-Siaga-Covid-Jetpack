package com.tilikki.bnccapp.siagacovid.overview

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.tilikki.bnccapp.R
import com.tilikki.bnccapp.siagacovid.PVContract
import com.tilikki.bnccapp.siagacovid.hotline.HotlineBottomDialogFragment
import com.tilikki.bnccapp.siagacovid.lookup.LookupActivity
import com.tilikki.bnccapp.siagacovid.utils.AppEventLogging
import kotlinx.android.synthetic.main.activity_corona_overview.*
import kotlinx.android.synthetic.main.bottom_sheet_summary_menu.*

class OverviewActivity : AppCompatActivity(), PVContract.ObjectView<OverviewData> {

    private val presenter = OverviewPresenter(OverviewModel(), this)

    companion object {
        const val callLookupActivity = "GOTO_LOOKUP_ACTIVITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_corona_overview)
        setupBottomSheet()
        fetchData()

        clLookupButton.setOnClickListener {
            gotoLookupActivity()
        }

        clHotlineButton.setOnClickListener {
            gotoHotlineActivity()
        }

        ibInfoIcon.setOnClickListener {
            Toast.makeText(this, "Coming soon!", Toast.LENGTH_SHORT).show()
        }

        ibReloadIcon.setOnClickListener {
            fetchData()
        }
    }

    override fun onBackPressed() {
        if (BottomSheetBehavior.from(bottomSheetSummaryView).state == BottomSheetBehavior.STATE_EXPANDED) {
            BottomSheetBehavior.from(bottomSheetSummaryView).state =
                BottomSheetBehavior.STATE_COLLAPSED
        } else {
            super.onBackPressed()
        }
    }

    private fun fetchData() {
        presenter.fetchData()
        toggleFetchState(true)
    }

    private fun toggleFetchState(isFetching: Boolean) {
        toggleFetchState(isFetching, tvTotalCaseCount, pbTotalCase)
        toggleFetchState(isFetching, tvPositiveCaseCount, pbPositive)
        toggleFetchState(isFetching, tvRecoveredCount, pbRecovered)
        toggleFetchState(isFetching, tvDeathCount, pbDeath)
    }

    private fun setupBottomSheet() {
        BottomSheetBehavior.from(bottomSheetSummaryView).apply {
            isHideable = false
            val dpi = Resources.getSystem().displayMetrics.density
            val peekHeightDPI = (250 * dpi).toInt()
            peekHeight = Resources.getSystem().displayMetrics.heightPixels - peekHeightDPI
        }
    }

    private fun gotoLookupActivity() {
        val intent = Intent(this, LookupActivity::class.java).apply {
            putExtra(callLookupActivity, "Go to lookup activity...")
        }
        startActivity(intent)
    }

    private fun gotoHotlineActivity() {
        HotlineBottomDialogFragment.show(supportFragmentManager)
    }

    override fun updateData(objectData: OverviewData) {
        runOnUiThread {
            tvTotalCaseCount.text = "${objectData.totalConfirmedCase}"
            tvPositiveCaseCount.text = "${objectData.totalActiveCase}"
            tvRecoveredCount.text = "${objectData.totalRecoveredCase}"
            tvDeathCount.text = "${objectData.totalDeathCase}"
            toggleFetchState(false)
        }
    }

    override fun showError(tag: String, e: Exception) {
        runOnUiThread {
            AppEventLogging.logExceptionOnToast(tag, this@OverviewActivity, e)
        }
    }

    private fun toggleFetchState(isFetching: Boolean, textView: TextView, progressBar: ProgressBar) {
        if (isFetching) {
            textView.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        } else {
            textView.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }
}