package com.tilikki.bnccapp.siagacovid.lookup

import com.tilikki.bnccapp.siagacovid.PVContract
import com.tilikki.bnccapp.siagacovid.utils.AppEventLogging
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class LookupPresenter(
    private val model: LookupModel,
    private val view: PVContract.View<LookupData>
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
                    val jsonArray = JSONObject(jsonString).getJSONArray("list_data")
                    val lookupDataFromNetwork = mutableListOf<LookupData>()

                    for (i in 0 until jsonArray.length()) {
                        val attribute = jsonArray.getJSONObject(i)
                        val dailyCases = attribute.getJSONObject("penambahan")
                        lookupDataFromNetwork.add(
                            LookupData(
                                provinceName = attribute.getString("key"),
                                numOfPositiveCase = attribute.getInt("jumlah_kasus"),
                                numOfRecoveredCase = attribute.getInt("jumlah_sembuh"),
                                numOfDeathCase = attribute.getInt("jumlah_meninggal"),
                                numOfDailyPositiveCase = dailyCases.getInt("positif"),
                                numOfDailyRecoveredCase = dailyCases.getInt("sembuh"),
                                numOfDailyDeathCase = dailyCases.getInt("meninggal")
                            )
                        )
                    }

                    view.updateData(lookupDataFromNetwork)
                } catch (e: Exception) {
                    view.showError(AppEventLogging.FETCH_FAILURE, e)
                }
            }
        }
    }
}