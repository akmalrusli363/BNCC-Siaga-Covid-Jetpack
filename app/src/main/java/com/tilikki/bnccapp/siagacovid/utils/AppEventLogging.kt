package com.tilikki.bnccapp.siagacovid.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast

class AppEventLogging {
    companion object {
        const val FETCH_FAILURE = "OkHttpApiDataFetch"

        fun logExceptionOnToast(tag: String, activity: Activity, e: Exception) {
            activity.runOnUiThread {
                Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                Log.e(tag, e.message, e)
            }
        }
    }
}