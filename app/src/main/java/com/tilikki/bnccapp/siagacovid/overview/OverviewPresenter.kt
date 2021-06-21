package com.tilikki.bnccapp.siagacovid.overview

import com.tilikki.bnccapp.siagacovid.PVContract
import com.tilikki.bnccapp.siagacovid.utils.AppEventLogging
import com.tilikki.bnccapp.siagacovid.utils.StringParser
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
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
                    val jsonString = response.body()!!.string()
                    val jsonObject = JSONObject(jsonString).getJSONObject("update")
                    val dailyUpdate = jsonObject.getJSONObject("penambahan")
                    val cumulativeUpdate = jsonObject.getJSONObject("total")
                    val overviewData = OverviewData(
                        totalConfirmedCase = StringParser.parseInt(
                            cumulativeUpdate.getString("jumlah_positif")
                        ),
                        totalActiveCase = StringParser.parseInt(
                            cumulativeUpdate.getString("jumlah_dirawat")
                        ),
                        totalRecoveredCase = StringParser.parseInt(
                            cumulativeUpdate.getString("jumlah_sembuh")
                        ),
                        totalDeathCase = StringParser.parseInt(
                            cumulativeUpdate.getString("jumlah_meninggal")
                        ),
                        dailyConfirmedCase = StringParser.parseInt(
                            dailyUpdate.getString("jumlah_positif")
                        ),
                        dailyActiveCase = StringParser.parseInt(
                            dailyUpdate.getString("jumlah_dirawat")
                        ),
                        dailyRecoveredCase = StringParser.parseInt(
                            dailyUpdate.getString("jumlah_sembuh")
                        ),
                        dailyDeathCase = StringParser.parseInt(
                            dailyUpdate.getString("jumlah_meninggal")
                        ),
                        lastUpdated = StringParser.parseDate(
                            dailyUpdate.getString("created")
                        )
                    )

                    view.updateData(overviewData)
                } catch (e: Exception) {
                    view.showError(AppEventLogging.FETCH_FAILURE, e)
                }
            }
        }
    }
}
