package com.tilikki.bnccapp.siagacovid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tilikki.bnccapp.R
import com.tilikki.bnccapp.siagacovid.hotline.HotlineActivity
import com.tilikki.bnccapp.siagacovid.lookup.LookupActivity
import kotlinx.android.synthetic.main.activity_corona_overview.*

class OverviewActivity : AppCompatActivity() {

//    private var returnPoint;

    companion object {
        const val callLookupActivity = "GOTO_LOOKUP_ACTIVITY"
        const val callHotlineActivity = "GOTO_HOTLINE_ACTIVITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_corona_overview)

        clLookupButton.setOnClickListener {
            gotoLookupActivity()
        }

        clHotlineButton.setOnClickListener {
            gotoHotlineActivity()
        }
    }

    private fun gotoLookupActivity() {
        val intent = Intent(this, LookupActivity::class.java).apply {
            putExtra(callLookupActivity, "Go to lookup activity...")
        }
        startActivity(intent)
    }

    private fun gotoHotlineActivity() {
        val intent = Intent(this, HotlineActivity::class.java).apply {
            putExtra(callHotlineActivity, "Go to hotline activity...")
        }
        startActivity(intent)
    }
}