package com.tilikki.bnccapp.siagacovid.hotline

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tilikki.bnccapp.R
import kotlinx.android.synthetic.main.activity_hotline.*
import okhttp3.*
import org.json.JSONArray
import java.io.IOException

class HotlineActivity : AppCompatActivity() {
    private val okHttpClient = OkHttpClient()

    private val mockHotlineList = mutableListOf(
        HotlineData("@drawable/ic_wait", "Loading...", "???")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotline)

        val hotlineAdapter = HotlineAdapter(mockHotlineList)
        rvHotline.layoutManager = LinearLayoutManager(this)
        rvHotline.adapter = hotlineAdapter

        fetchData(hotlineAdapter)

        ivReturnIcon.setOnClickListener {
            finish()
        }
    }

    private fun fetchData(hotlineAdapter: HotlineAdapter) {
        val request: Request = Request.Builder()
            .url("https://bncc-corona-versus.firebaseio.com/v1/hotlines.json")
            .build()

        okHttpClient.newCall(request).enqueue(getCallback(hotlineAdapter))
    }

    private fun getCallback(hotlineAdapter: HotlineAdapter): Callback {
        return object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                this@HotlineActivity.runOnUiThread {
                    Toast.makeText(this@HotlineActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val jsonString = response.body?.string()
                    val jsonArray = JSONArray(jsonString)
                    val hotlineListFromNetwork = mutableListOf<HotlineData>()

                    for (i in 0 until jsonArray.length()) {
                        hotlineListFromNetwork.add(
                            HotlineData(
                                imgIcon = jsonArray.getJSONObject(i).getString("img_icon"),
                                name = jsonArray.getJSONObject(i).getString("name"),
                                phone = jsonArray.getJSONObject(i).getString("phone")
                            )
                        )
                    }

                    this@HotlineActivity.runOnUiThread {
                        hotlineAdapter.updateData(hotlineListFromNetwork)
                    }
                } catch (e: Exception) {
                    this@HotlineActivity.runOnUiThread {
                        Toast.makeText(this@HotlineActivity, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }
}
