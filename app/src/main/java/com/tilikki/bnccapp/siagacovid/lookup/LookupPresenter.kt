package com.tilikki.bnccapp.siagacovid.lookup

import com.tilikki.bnccapp.siagacovid.PVContract
import com.tilikki.bnccapp.siagacovid.utils.AppEventLogging
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONArray
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

                    view.updateData(lookupDataFromNetwork)
                } catch (e: Exception) {
                    view.showError(AppEventLogging.FETCH_FAILURE, e)
                }
            }
        }
    }
}