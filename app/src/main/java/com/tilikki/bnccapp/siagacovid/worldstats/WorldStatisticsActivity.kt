package com.tilikki.bnccapp.siagacovid.worldstats

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.tilikki.bnccapp.R
import kotlinx.android.synthetic.main.activity_lookup.*
import kotlinx.android.synthetic.main.bottom_sheet_country_lookup.*

class WorldStatisticsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_world_statistics)
        setupReturnButton()
        setupBottomSheet()
    }

    private fun setupReturnButton() {
        ivReturnIcon.setOnClickListener {
            finish()
        }
    }

    private fun setupBottomSheet() {
        BottomSheetBehavior.from(bottomSheetWorldLookup).apply {
            isHideable = false
            val dpi = Resources.getSystem().displayMetrics.density
            val peekHeightDPI = (250 * dpi).toInt()
            peekHeight = Resources.getSystem().displayMetrics.heightPixels - peekHeightDPI
        }
    }
}