package com.tilikki.bnccapp.siagacovid.overview

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
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

    companion object {
        const val callLookupActivity = "GOTO_LOOKUP_ACTIVITY"
        const val callHotlineActivity = "GOTO_HOTLINE_ACTIVITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_corona_overview)
        setupBottomSheet()
        OverviewPresenter(OverviewModel(), this).fetchData()

        clLookupButton.setOnClickListener {
            gotoLookupActivity()
        }

        clHotlineButton.setOnClickListener {
            gotoHotlineActivity()
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

    override fun updateData(data: OverviewData) {
        runOnUiThread {
            tvTotalCaseCount.text = "${data.totalConfirmedCase}"
            tvActiveCaseCount.text = "${data.totalActiveCase}"
            tvRecoveredCount.text = "${data.totalRecoveredCase}"
            tvDeathCount.text = "${data.totalDeathCase}"
        }
    }

    override fun showError(tag: String, e: Exception) {
        runOnUiThread {
            AppEventLogging.logExceptionOnToast(tag, this@OverviewActivity, e)
        }
    }
}