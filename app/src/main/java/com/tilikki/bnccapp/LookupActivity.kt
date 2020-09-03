package com.tilikki.bnccapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_lookup.*
import kotlinx.android.synthetic.main.activity_main.*

class LookupActivity : AppCompatActivity() {
    companion object {
        const val callLookupActivity = "RETURN_TO_OVERVIEW_ACTIVITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lookup)

        returnIcon.setOnClickListener {
            returnToOverview()
        }
    }

    private fun returnToOverview() {
        val intent = Intent(this, CoronaData::class.java).apply {
            putExtra(callLookupActivity, "Return to Overview...")
        }
        startActivity(intent)
    }
}