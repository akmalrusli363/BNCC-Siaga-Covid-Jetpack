package com.tilikki.bnccapp.siagacovid.lookup

import com.tilikki.bnccapp.siagacovid.utils.RegionParser
import okhttp3.Response
import org.json.JSONObject

class GovernmentLookupModel : LookupModel() {
    override fun apiURL(): String = lookupDataApiURL
    override fun apiProvider(): String = provider

    override fun obtainData(response: Response): MutableList<LookupData> {
        val jsonString = response.body!!.string()
        val jsonArray = JSONObject(jsonString).getJSONArray("list_data")
        val lookupDataFromNetwork = mutableListOf<LookupData>()

        for (i in 0 until jsonArray.length()) {
            val attribute = jsonArray.getJSONObject(i)
            val dailyCases = attribute.getJSONObject("penambahan")
            lookupDataFromNetwork.add(
                LookupData(
                    provinceName = RegionParser.capitalizeRegionName(
                        attribute.getString("key")
                    ),
                    numOfPositiveCase = attribute.getInt("jumlah_kasus"),
                    numOfRecoveredCase = attribute.getInt("jumlah_sembuh"),
                    numOfDeathCase = attribute.getInt("jumlah_meninggal"),
                    numOfDailyPositiveCase = dailyCases.getInt("positif"),
                    numOfDailyRecoveredCase = dailyCases.getInt("sembuh"),
                    numOfDailyDeathCase = dailyCases.getInt("meninggal")
                )
            )
        }
        return lookupDataFromNetwork
    }

    companion object {
        const val lookupDataApiURL = "https://data.covid19.go.id/public/api/prov.json"
        const val provider = "data.covid19.go.id"
    }
}