package com.tilikki.bnccapp.siagacovid.utils

import android.app.Activity
import android.content.Context
import android.widget.Toast

class AppEventLogging {
    companion object {
        fun logExceptionOnToast(activity: Activity, e: Exception) {
            activity.runOnUiThread {
                Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }
}