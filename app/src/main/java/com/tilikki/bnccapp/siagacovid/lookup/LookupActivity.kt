package com.tilikki.bnccapp.siagacovid.lookup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.tilikki.bnccapp.R
import kotlinx.android.synthetic.main.activity_lookup.*

class LookupActivity : AppCompatActivity() {
    companion object {
        const val callLookupActivity = "RETURN_TO_OVERVIEW_ACTIVITY"
    }

    private val mockLookupList = mutableListOf(
        LookupData("DKI Jakarta", 16538, 1044, 736),
        LookupData("Jawa Timur", 18308, 9342, 1401),
        LookupData("Sulawesi Selatan", 8039, 4198, 279),
        LookupData("Jawa Tengah", 12114, 5244, 464),
        LookupData("Kalimantan Barat", 3045, 2344, 403),
        LookupData("Papua", 2133, 1675, 95),
        LookupData("Bali", 3412, 2313, 101)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lookup)

        val lookupAdapter = LookupAdapter(mockLookupList)
        rvLookUp.layoutManager = LinearLayoutManager(this)
        rvLookUp.adapter = lookupAdapter

        ivReturnIcon.setOnClickListener {
            finish()
        }
    }
}