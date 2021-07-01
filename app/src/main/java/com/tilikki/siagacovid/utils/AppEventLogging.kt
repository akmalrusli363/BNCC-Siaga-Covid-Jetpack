package com.tilikki.siagacovid.utils

import android.app.Activity
import android.util.Log
import android.widget.Toast

object AppEventLogging {
    const val FETCH_FAILURE = "RetrofitApiDataFetch"

    fun logExceptionOnToast(tag: String, activity: Activity, e: Exception) {
        activity.runOnUiThread {
            Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
            Log.e(tag, e.message, e)
        }
    }
}
