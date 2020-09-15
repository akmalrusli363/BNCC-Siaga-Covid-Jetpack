package com.tilikki.bnccapp.siagacovid.lookup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.tilikki.bnccapp.R
import com.tilikki.bnccapp.siagacovid.utils.AppEventLogging
import kotlinx.android.synthetic.main.activity_lookup.*
import okhttp3.*
import org.json.JSONArray
import java.io.IOException

class LookupActivity : AppCompatActivity() {
    private val okHttpClient = OkHttpClient()

    companion object {
        const val lookupDataApiURL = "https://api.kawalcorona.com/indonesia/provinsi/"
    }

    private var mockLookupList: MutableList<LookupData> = mutableListOf(
        LookupData("Loading...", 0, 0, 0)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lookup)

        val lookupAdapter = LookupAdapter(mockLookupList)
        rvLookUp.layoutManager = LinearLayoutManager(this)
        rvLookUp.adapter = lookupAdapter

        fetchData(lookupAdapter)

        ivReturnIcon.setOnClickListener {
            finish()
        }

        ibClearSearch.setOnClickListener {
            etRegionLookupSearch.text.clear()
        }

        etRegionLookupSearch.addTextChangedListener {
            lookupAdapter.filter.filter(etRegionLookupSearch.text)
        }

        srlLookupData.setOnRefreshListener {
            fetchData(lookupAdapter)
        }
    }

    private fun fetchData(lookupAdapter: LookupAdapter) {
        val request: Request = Request.Builder().url(lookupDataApiURL).build()

        okHttpClient.newCall(request).enqueue(getCallback(lookupAdapter))
    }

    private fun getCallback(lookupAdapter: LookupAdapter): Callback {
        return object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                AppEventLogging.logExceptionOnToast(this@LookupActivity, e)
                srlLookupData.isRefreshing = false
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val jsonString = response.body?.string()
                    val jsonArray = JSONArray(jsonString)
                    val lookupDataFromNetwork = mutableListOf<LookupData>()

                    for (i in 0 until jsonArray.length()) {
                        val attribute = jsonArray.getJSONObject(i).getJSONObject("attributes")
                        lookupDataFromNetwork.add(
                            LookupData(
                                provinceID = attribute.getInt("Kode_Provi"),
                                provinceName = attribute.getString("Provinsi"),
                                numOfPositiveCase = attribute.getInt("Kasus_Posi"),
                                numOfRecoveredCase = attribute.getInt("Kasus_Semb"),
                                numOfDeathCase = attribute.getInt("Kasus_Meni")
                            )
                        )
                    }

                    this@LookupActivity.runOnUiThread {
                        lookupAdapter.updateData(lookupDataFromNetwork)
                        srlLookupData.isRefreshing = false
                    }
                } catch (e: Exception) {
                    AppEventLogging.logExceptionOnToast(this@LookupActivity, e)
                }
            }
        }
    }
}