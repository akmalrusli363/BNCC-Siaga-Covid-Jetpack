package com.tilikki.bnccapp.siagacovid.worldstats

import com.tilikki.bnccapp.siagacovid.PVContract
import com.tilikki.bnccapp.siagacovid.utils.AppEventLogging
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class WorldStatPresenter(
    private val model: WorldStatModel,
    private val view: PVContract.View<WorldStatLookupData>,
    private val objView: PVContract.ObjectView<WorldStatSummaryData>
) :
    PVContract.Presenter {
    override fun fetchData() {
        model.fetchData(getData())
    }

    private fun getData(): Callback {
        return object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                view.showError(AppEventLogging.FETCH_FAILURE, e)
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val jsonString = response.body()!!.string()
                    val jsonSummary = JSONObject(jsonString).getJSONObject("Global")
                    val jsonCountries = JSONObject(jsonString).getJSONArray("Countries")

                    var worldStatSummary: WorldStatSummaryData
                    jsonSummary.apply {
                        worldStatSummary = WorldStatSummaryData(
                            numOfConfirmedCase = getIntOrNothing("TotalConfirmed"),
                            numOfRecoveredCase = getIntOrNothing("TotalRecovered"),
                            numOfDeathCase = getIntOrNothing("TotalDeaths")
                        )
                    }
                    objView.updateData(worldStatSummary)
                    val worldStatDataFromNetwork = fetchCountryStatistics(jsonCountries)
                    view.updateData(worldStatDataFromNetwork)
                } catch (e: Exception) {
                    view.showError(AppEventLogging.FETCH_FAILURE, e)
                }
            }
        }
    }

    private fun fetchCountryStatistics(
        jsonCountries: JSONArray
    ): MutableList<WorldStatLookupData> {
        val worldStatDataFromNetwork: MutableList<WorldStatLookupData> = mutableListOf()
        for (i in 0 until jsonCountries.length()) {
            val attribute = jsonCountries.getJSONObject(i)
            attribute.apply {
                worldStatDataFromNetwork.add(
                    WorldStatLookupData(
                        countryCode = getString("CountryCode"),
                        countryName = getString("Country"),
                        numOfConfirmedCase = getIntOrNothing("TotalConfirmed"),
                        numOfRecoveredCase = getIntOrNothing("TotalRecovered"),
                        numOfDeathCase = getIntOrNothing("TotalDeaths"),
                        numOfDailyConfirmedCase = getIntOrNothing("NewConfirmed"),
                        numOfDailyRecoveredCase = getIntOrNothing("NewRecovered"),
                        numOfDailyDeathCase = getIntOrNothing("NewDeaths")
                    )
                )
            }
        }
        return worldStatDataFromNetwork
    }

    private fun JSONObject.getIntOrNothing(key: String): Int {
        return if (!this.checkForNullity(key)) this.getInt(key) else 0
    }

    private fun JSONObject.checkForNullity(key: String): Boolean {
        return this.isNull(key)
    }
}
