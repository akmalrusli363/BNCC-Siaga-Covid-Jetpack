package com.tilikki.bnccapp.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tilikki.bnccapp.siagacovid.OverviewActivity
import com.tilikki.bnccapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val callSecondActivity = "LAUNCH_ACTIVITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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