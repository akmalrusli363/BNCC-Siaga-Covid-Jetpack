package com.tilikki.bnccapp.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tilikki.bnccapp.siagacovid.overview.OverviewActivity
import com.tilikki.bnccapp.R
import kotlinx.android.synthetic.main.activity_demo_activity.*

class DemoAppActivity : AppCompatActivity() {
    companion object {
        const val callSecondActivity = "LAUNCH_ACTIVITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_activity)

        okButton.setOnClickListener {
            openCoronaActivity()
        }
    }

    private fun openCoronaActivity() {
        val intent = Intent(this, OverviewActivity::class.java).apply {
            putExtra(callSecondActivity, "Launching Corona App from Main Activity")
        }
        startActivity(intent)
    }
}