package com.tilikki.bnccapp.siagacovid.overview

import com.tilikki.bnccapp.siagacovid.PVContract
import com.tilikki.bnccapp.siagacovid.model.CaseOverview
import com.tilikki.bnccapp.siagacovid.utils.AppEventLogging
import retrofit2.HttpException

class OverviewPresenter(
    private val model: OverviewModel,
    private val view: PVContract.ObjectView<CaseOverview>
) : PVContract.Presenter {
    override fun fetchData() {
        try {
            val overviewData = model.fetchData()
            view.updateData(overviewData)
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
