package com.tilikki.bnccapp.siagacovid.overview

import com.tilikki.bnccapp.siagacovid.PVContract
import com.tilikki.bnccapp.siagacovid.utils.AppEventLogging
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class OverviewPresenter(
    private val model: OverviewModel,
    private val view: PVContract.ObjectView<OverviewData>
) :
    PVContract.Presenter {
    override fun fetchData() {
        model.fetchData(getData())
    }

    override fun getData(): Callback {
        return object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                view.showError(AppEventLogging.FETCH_FAILURE, e)
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val jsonString = response.body!!.string()
                    val jsonObject = JSONObject(jsonString).getJSONObject("update")
                    val dailyUpdate = jsonObject.getJSONObject("penambahan")
                    val cumulativeUpdate = jsonObject.getJSONObject("total")
                    val overviewData = OverviewData(
                        totalConfirmedCase = parseInt(cumulativeUpdate.getString("jumlah_positif")),
                        totalActiveCase = parseInt(cumulativeUpdate.getString("jumlah_dirawat")),
                        totalRecoveredCase = parseInt(cumulativeUpdate.getString("jumlah_sembuh")),
                        totalDeathCase = parseInt(cumulativeUpdate.getString("jumlah_meninggal")),
                        dailyConfirmedCase = parseInt(dailyUpdate.getString("jumlah_positif")),
                        dailyActiveCase = parseInt(dailyUpdate.getString("jumlah_dirawat")),
                        dailyRecoveredCase = parseInt(dailyUpdate.getString("jumlah_sembuh")),
                        dailyDeathCase = parseInt(dailyUpdate.getString("jumlah_meninggal"))
                    )

                    view.updateData(overviewData)
                } catch (e: Exception) {
                    view.showError(AppEventLogging.FETCH_FAILURE, e)
                }
            }
        }
    }

    private fun parseInt(str : String): Int {
        return str.replace(",", "").toInt()
    }
}