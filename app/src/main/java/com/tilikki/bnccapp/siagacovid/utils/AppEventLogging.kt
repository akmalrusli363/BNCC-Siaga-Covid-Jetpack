package com.tilikki.bnccapp.siagacovid.utils

import android.app.Activity
import android.widget.Toast
import java.lang.Exception

class AppEventLogging(private val activity: Activity) {
    fun logExceptionOnToast(e: Exception) {
        activity.runOnUiThread {
            Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }
}