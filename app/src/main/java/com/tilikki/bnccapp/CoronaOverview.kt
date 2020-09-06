package com.tilikki.bnccapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_corona_overview.*

class CoronaOverview : AppCompatActivity() {

//    private var returnPoint;

    companion object {
        const val callLookupActivity = "GOTO_LOOKUP_ACTIVITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_corona_overview)

        lookupButton.setOnClickListener {
            gotoLookupActivity()
        }
    }

    private fun gotoLookupActivity() {
        val intent = Intent(this, LookupActivity::class.java).apply {
            putExtra(callLookupActivity, "Go to lookup activity...")
        }
        startActivity(intent)
    }
}