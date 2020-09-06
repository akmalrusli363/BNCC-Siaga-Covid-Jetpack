package com.tilikki.bnccapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_lookup.*

class LookupActivity : AppCompatActivity() {
    companion object {
        const val callLookupActivity = "RETURN_TO_OVERVIEW_ACTIVITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lookup)

        ivReturnIcon.setOnClickListener {
            finish()
        }
    }
}