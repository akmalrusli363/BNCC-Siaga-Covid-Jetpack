package com.tilikki.bnccapp.siagacovid.lookup

import com.tilikki.bnccapp.siagacovid.utils.RegionParser
import okhttp3.Response
import org.json.JSONArray

class ArcGISLookupModel : LookupModel() {
    override fun apiURL(): String = lookupDataApiURL
    override fun apiProvider(): String = provider

    override fun obtainData(response: Response): LookupSummaryData {
        val jsonString = response.body!!.string()
        val jsonArray = JSONArray(jsonString)
        val lookupDataFromNetwork = mutableListOf<LookupData>()

        for (i in 0 until jsonArray.length()) {
            val attribute = jsonArray.getJSONObject(i).getJSONObject("attributes")
            lookupDataFromNetwork.add(
                LookupData(
                    provinceName = RegionParser.capitalizeRegionName(
                        attribute.getString("Provinsi")
                    ),
                    numOfPositiveCase = attribute.getInt("Kasus_Posi"),
                    numOfRecoveredCase = attribute.getInt("Kasus_Semb"),
                    numOfDeathCase = attribute.getInt("Kasus_Meni"),
                    numOfDailyPositiveCase = -1,
                    numOfDailyRecoveredCase = -1,
                    numOfDailyDeathCase = -1
                )
            )
        }
        return LookupSummaryData(lookupDataFromNetwork, apiProvider(), null)
    }

    companion object {
        const val lookupDataApiURL = "https://api.kawalcorona.com/indonesia/provinsi/"
        const val provider = "BNPB Indonesia"
    }
}