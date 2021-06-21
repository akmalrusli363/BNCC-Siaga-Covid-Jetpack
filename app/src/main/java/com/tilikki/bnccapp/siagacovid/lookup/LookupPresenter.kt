package com.tilikki.bnccapp.siagacovid.lookup

import com.tilikki.bnccapp.siagacovid.PVContract
import com.tilikki.bnccapp.siagacovid.lookup.netmodel.RegionSummaryData
import com.tilikki.bnccapp.siagacovid.utils.AppEventLogging
import retrofit2.HttpException

class LookupPresenter(
    private val model: LookupModel,
    private val view: PVContract.ObjectView<RegionSummaryData>
) : PVContract.Presenter {
    override fun fetchData() {
        try {
            val lookupData = model.fetchData()
            view.updateData(lookupData)
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    val message = "${e.code()} - ${e.message()}"
                    view.showError(AppEventLogging.FETCH_FAILURE, Exception(message))
                }
                else -> view.showError(AppEventLogging.FETCH_FAILURE, e)
            }
        }
    }
}
