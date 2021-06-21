package com.tilikki.bnccapp.siagacovid.hotline

import com.tilikki.bnccapp.siagacovid.PVContract
import com.tilikki.bnccapp.siagacovid.utils.AppEventLogging
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException

class HotlinePresenter(
    private val model: HotlineModel,
    private val view: PVContract.View<HotlineData>
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
                    val jsonString = response.body()?.string()
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


                    view.updateData(hotlineListFromNetwork)
                } catch (e: Exception) {
                    view.showError(AppEventLogging.FETCH_FAILURE, e)
                }
            }

        }
    }
}
