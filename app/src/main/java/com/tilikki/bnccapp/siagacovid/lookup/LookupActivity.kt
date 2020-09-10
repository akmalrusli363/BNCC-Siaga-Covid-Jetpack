package com.tilikki.bnccapp.siagacovid.lookup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.tilikki.bnccapp.R
import kotlinx.android.synthetic.main.activity_lookup.*

class LookupActivity : AppCompatActivity() {
    private lateinit var mockLookupList : MutableList<LookupData>

    fun fetchRegionData() {
        mockLookupList = mutableListOf(
            LookupData("${getText(R.string.province_dki_jakarta)}", 16538, 1044, 736),
            LookupData("${getText(R.string.province_jawa_timur)}", 18308, 9342, 1401),
            LookupData("${getText(R.string.province_sulawesi_selatan)}", 8039, 4198, 279),
            LookupData("${getText(R.string.province_jawa_tengah)}", 12114, 5244, 464),
            LookupData("${getText(R.string.province_kalimantan_barat)}", 3045, 2344, 403),
            LookupData("${getText(R.string.province_papua)}", 2133, 1675, 95),
            LookupData("${getText(R.string.province_bali)}", 3412, 2313, 101)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lookup)
        fetchRegionData()

        val lookupAdapter = LookupAdapter(mockLookupList)
        rvLookUp.layoutManager = LinearLayoutManager(this)
        rvLookUp.adapter = lookupAdapter

        ivReturnIcon.setOnClickListener {
            finish()
        }

        ibClearSearch.setOnClickListener {
            etRegionLookupSearch.text.clear()
        }

        etRegionLookupSearch.addTextChangedListener{
            lookupAdapter.filter.filter(etRegionLookupSearch.text)
        }
    }
}