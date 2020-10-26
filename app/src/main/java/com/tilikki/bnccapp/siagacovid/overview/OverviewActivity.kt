package com.tilikki.bnccapp.siagacovid.overview

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.tilikki.bnccapp.R
import com.tilikki.bnccapp.siagacovid.PVContract
import com.tilikki.bnccapp.siagacovid.about.AboutAppDialog
import com.tilikki.bnccapp.siagacovid.hotline.HotlineBottomDialogFragment
import com.tilikki.bnccapp.siagacovid.lookup.LookupActivity
import com.tilikki.bnccapp.siagacovid.utils.AppEventLogging
import com.tilikki.bnccapp.siagacovid.utils.StringParser
import com.tilikki.bnccapp.siagacovid.worldstats.WorldStatisticsActivity
import kotlinx.android.synthetic.main.activity_corona_overview.*
import kotlinx.android.synthetic.main.bottom_sheet_summary_menu.*
import kotlin.math.absoluteValue

class OverviewActivity : AppCompatActivity(), PVContract.ObjectView<OverviewData> {

    private val presenter = OverviewPresenter(OverviewModel(), this)

    companion object {
        const val callLookupActivity = "GOTO_LOOKUP_ACTIVITY"
        const val callWorldStatActivity = "GOTO_WORLD_STATISTICS_ACTIVITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_corona_overview)
        setupBottomSheet()
        setupUiButtons()
        fetchData()
    }

    override fun onBackPressed() {
        if (BottomSheetBehavior.from(bottomSheetSummaryView).state == BottomSheetBehavior.STATE_EXPANDED) {
            BottomSheetBehavior.from(bottomSheetSummaryView).state =
                BottomSheetBehavior.STATE_COLLAPSED
        } else {
            super.onBackPressed()
        }
    }

    private fun setupUiButtons() {
        clLookupButton.setOnClickListener {
            gotoLookupActivity()
        }

        clHotlineButton.setOnClickListener {
            gotoHotlineActivity()
        }

        clWorldStatsButton.setOnClickListener {
            gotoWorldStatActivity()
        }

        ibInfoIcon.setOnClickListener {
            openAboutDialog()
        }

        ibReloadIcon.setOnClickListener {
            fetchData()
        }
    }

    private fun fetchData() {
        presenter.fetchData()
        toggleFetchState(true)
    }

    private fun toggleFetchState(isFetching: Boolean) {
        toggleFetchState(isFetching, tvDailyTotalCaseCount, tvTotalCaseCount, pbTotalCase)
        toggleFetchState(isFetching, tvDailyPositiveCount, tvPositiveCount, pbPositive)
        toggleFetchState(isFetching, tvDailyRecoveredCount, tvRecoveredCount, pbRecovered)
        toggleFetchState(isFetching, tvDailyDeathCount, tvDeathCount, pbDeath)
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

    private fun gotoWorldStatActivity() {
        val intent = Intent(this, WorldStatisticsActivity::class.java).apply {
            putExtra(callWorldStatActivity, "Go to world statistics activity...")
        }
        startActivity(intent)
    }

    private fun openAboutDialog() {
        AboutAppDialog().show(supportFragmentManager, "aboutAppDialog")
    }

    override fun updateData(objectData: OverviewData) {
        runOnUiThread {
            tvTotalCaseCount.text = "${objectData.totalConfirmedCase}"
            tvPositiveCount.text = "${objectData.totalActiveCase}"
            tvRecoveredCount.text = "${objectData.totalRecoveredCase}"
            tvDeathCount.text = "${objectData.totalDeathCase}"

            tvDailyTotalCaseCount.text = displayDailyCaseCount(objectData.dailyConfirmedCase)
            tvDailyPositiveCount.text = displayDailyCaseCount(objectData.dailyActiveCase)
            tvDailyRecoveredCount.text = displayDailyCaseCount(objectData.dailyRecoveredCase)
            tvDailyDeathCount.text = displayDailyCaseCount(objectData.dailyDeathCase)

            tvLastUpdated.text = getString(R.string.last_updated)
                .replace("???", StringParser.formatDate(objectData.lastUpdated))

            toggleFetchState(false)
        }
    }

    override fun showError(tag: String, e: Exception) {
        runOnUiThread {
            AppEventLogging.logExceptionOnToast(tag, this@OverviewActivity, e)
        }
    }

    private fun toggleFetchState(
        isFetching: Boolean,
        tvDaily: TextView,
        tvCumulative: TextView,
        progressBar: ProgressBar
    ) {
        if (isFetching) {
            tvDaily.visibility = View.GONE
            tvCumulative.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        } else {
            tvDaily.visibility = View.VISIBLE
            tvCumulative.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }

    private fun displayDailyCaseCount(dailyCaseCount: Int): String {
        val sign = when {
            dailyCaseCount < 0 -> '-'
            else -> '+'
        }
        return "(${sign}${dailyCaseCount.absoluteValue})"
    }
}