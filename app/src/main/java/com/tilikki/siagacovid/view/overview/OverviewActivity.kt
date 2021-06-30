package com.tilikki.siagacovid.view.overview

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.tilikki.siagacovid.R
import com.tilikki.siagacovid.databinding.ActivityCoronaOverviewBinding
import com.tilikki.siagacovid.model.CaseOverview
import com.tilikki.siagacovid.utils.AppEventLogging
import com.tilikki.siagacovid.utils.StringParser
import com.tilikki.siagacovid.utils.ViewUtility
import com.tilikki.siagacovid.view.about.AboutAppDialog
import com.tilikki.siagacovid.view.hotline.HotlineBottomDialogFragment
import com.tilikki.siagacovid.view.lookup.LookupActivity
import com.tilikki.siagacovid.view.vaccine.VaccinationActivity
import com.tilikki.siagacovid.view.worldstats.WorldStatisticsActivity

class OverviewActivity : AppCompatActivity() {
    private lateinit var viewModel: OverviewViewModel
    private lateinit var binding: ActivityCoronaOverviewBinding

    companion object {
        const val callLookupActivity = "GOTO_LOOKUP_ACTIVITY"
        const val callVaccinationActivity = "GOTO_VACCINATION_ACTIVITY"
        const val callWorldStatActivity = "GOTO_WORLD_STATISTICS_ACTIVITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoronaOverviewBinding.inflate(layoutInflater)
        viewModel = OverviewViewModel()
        setContentView(binding.root)
        setupBottomSheet()
        setupUiButtons()
        fetchData()
        startObserve()
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

    private fun setupUiButtons() {
        binding.apply {
            bottomSheetSummaryView.apply {
                clLookupButton.setOnClickListener { gotoLookupActivity() }
                clVaccinationButton.setOnClickListener { gotoVaccinationActivity() }
                clHotlineButton.setOnClickListener { gotoHotlineActivity() }
                clWorldStatsButton.setOnClickListener { gotoWorldStatActivity() }
            }
            ibInfoIcon.setOnClickListener { openAboutDialog() }
            ibReloadIcon.setOnClickListener { fetchData() }
        }
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

    private fun gotoVaccinationActivity() {
        val intent = Intent(this, VaccinationActivity::class.java).apply {
            putExtra(callWorldStatActivity, "Go to vaccination activity...")
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

    private fun fetchData() {
        toggleFetchState(true)
        viewModel.fetchData()
    }

    private fun startObserve() {
        viewModel.overviewData.observe(this) {
            updateCaseCountData(it)
        }
        viewModel.successResponse.observe(this) {
            if (!it.success && it.error != null) {
                showError(AppEventLogging.FETCH_FAILURE, it.error as Exception)
            }
        }
    }

    private fun updateCaseCountData(data: CaseOverview) {
        binding.apply {
            ViewUtility.setStatisticPairs(data.confirmedCase, tvTotalCaseCount, tvDailyTotalCaseCount)
            binding.bottomSheetSummaryView.apply {
                ViewUtility.setStatisticPairs(data.activeCase, tvPositiveCount, tvDailyPositiveCount)
                ViewUtility.setStatisticPairs(data.recoveredCase, tvRecoveredCount, tvDailyRecoveredCount)
                ViewUtility.setStatisticPairs(data.deathCase, tvDeathCount, tvDailyDeathCount)
                tvLastUpdated.text = getString(R.string.last_updated)
                    .replace("???", StringParser.formatDate(data.lastUpdated))
            }
            toggleFetchState(false)
        }
    }

    private fun showError(tag: String, e: Exception) {
        AppEventLogging.logExceptionOnToast(tag, this@OverviewActivity, e)
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
}
