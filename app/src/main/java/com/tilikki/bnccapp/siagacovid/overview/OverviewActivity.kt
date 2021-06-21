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
import com.tilikki.bnccapp.databinding.ActivityCoronaOverviewBinding
import com.tilikki.bnccapp.siagacovid.PVContract
import com.tilikki.bnccapp.siagacovid.about.AboutAppDialog
import com.tilikki.bnccapp.siagacovid.hotline.HotlineBottomDialogFragment
import com.tilikki.bnccapp.siagacovid.lookup.LookupActivity
import com.tilikki.bnccapp.siagacovid.model.CaseOverview
import com.tilikki.bnccapp.siagacovid.utils.AppEventLogging
import com.tilikki.bnccapp.siagacovid.utils.StringParser
import kotlin.math.absoluteValue

class OverviewActivity : AppCompatActivity(), PVContract.ObjectView<CaseOverview> {
    private val presenter = OverviewPresenter(OverviewModel(), this)

    private lateinit var binding: ActivityCoronaOverviewBinding

    companion object {
        const val callLookupActivity = "GOTO_LOOKUP_ACTIVITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoronaOverviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomSheet()
        setupUiButtons()
        fetchData()
    }

    override fun onBackPressed() {
        binding.bottomSheetSummaryView.apply {
            if (BottomSheetBehavior.from(this.root).state == BottomSheetBehavior.STATE_EXPANDED) {
                BottomSheetBehavior.from(this.root).state =
                    BottomSheetBehavior.STATE_COLLAPSED
            } else {
                super.onBackPressed()
            }
        }
    }

    override fun updateData(objectData: CaseOverview) {
        runOnUiThread {
            updateCaseCountData(objectData)
        }
    }

    private fun setupUiButtons() {
        binding.apply {
            ibInfoIcon.setOnClickListener { openAboutDialog() }
            ibReloadIcon.setOnClickListener { fetchData() }
            bottomSheetSummaryView.clLookupButton.setOnClickListener { gotoLookupActivity() }
            bottomSheetSummaryView.clHotlineButton.setOnClickListener { gotoHotlineActivity() }
        }
    }

    private fun fetchData() {
        presenter.fetchData()
        toggleFetchState(true)
    }

    private fun setupBottomSheet() {
        BottomSheetBehavior.from(binding.bottomSheetSummaryView.root).apply {
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

    private fun openAboutDialog() {
        AboutAppDialog().show(supportFragmentManager, "aboutAppDialog")
    }

    private fun updateCaseCountData(objectData: CaseOverview) {
        binding.apply {
            tvTotalCaseCount.text = "${objectData.totalConfirmedCase}"
            tvDailyTotalCaseCount.text = displayDailyCaseCount(objectData.dailyConfirmedCase)
            binding.bottomSheetSummaryView.apply {
                tvPositiveCount.text = "${objectData.totalActiveCase}"
                tvRecoveredCount.text = "${objectData.totalRecoveredCase}"
                tvDeathCount.text = "${objectData.totalDeathCase}"

                tvDailyPositiveCount.text = displayDailyCaseCount(objectData.dailyActiveCase)
                tvDailyRecoveredCount.text = displayDailyCaseCount(objectData.dailyRecoveredCase)
                tvDailyDeathCount.text = displayDailyCaseCount(objectData.dailyDeathCase)

                tvLastUpdated.text = getString(R.string.last_updated)
                    .replace("???", StringParser.formatDate(objectData.lastUpdated))
            }
            toggleFetchState(false)
        }
    }

    override fun showError(tag: String, e: Exception) {
        runOnUiThread {
            AppEventLogging.logExceptionOnToast(tag, this@OverviewActivity, e)
        }
    }

    private fun toggleFetchState(isFetching: Boolean) {
        binding.apply {
            toggleFetchState(isFetching, tvDailyTotalCaseCount, tvTotalCaseCount, pbTotalCase)
            bottomSheetSummaryView.apply {
                toggleFetchState(isFetching, tvDailyPositiveCount, tvPositiveCount, pbPositive)
                toggleFetchState(isFetching, tvDailyRecoveredCount, tvRecoveredCount, pbRecovered)
                toggleFetchState(isFetching, tvDailyDeathCount, tvDeathCount, pbDeath)
            }
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
