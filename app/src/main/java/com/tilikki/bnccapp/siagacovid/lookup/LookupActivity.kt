package com.tilikki.bnccapp.siagacovid.lookup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.tilikki.bnccapp.R
import com.tilikki.bnccapp.siagacovid.PVContract
import com.tilikki.bnccapp.siagacovid.utils.AppEventLogging
import kotlinx.android.synthetic.main.activity_lookup.*

class LookupActivity : AppCompatActivity(), PVContract.View<LookupData> {
    private val presenter = LookupPresenter(LookupModel(), this)

    private var mockLookupList: MutableList<LookupData> = mutableListOf(
        LookupData("Loading...", 0, 0, 0)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lookup)
        setupRecyclerAdapter()
        setupReturnButton()
        setupSearch(lookupAdapter)
    }

    private val lookupAdapter = LookupAdapter(mockLookupList)

    private fun setupRecyclerAdapter() {
        rvLookUp.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvLookUp.adapter = lookupAdapter
        fetchData()
    }

    private fun setupReturnButton() {
        ivReturnIcon.setOnClickListener {
            finish()
        }
    }

    private fun setupSearch(lookupAdapter: LookupAdapter) {
        ibClearSearch.setOnClickListener {
            etRegionLookupSearch.text.clear()
        }

        etRegionLookupSearch.addTextChangedListener {
            lookupAdapter.filter.filter(etRegionLookupSearch.text)
        }

        srlLookupData.setOnRefreshListener {
            fetchData()
        }
    }

    private fun fetchData() {
        presenter.fetchData()
    }

    override fun updateData(listData: List<LookupData>) {
        this@LookupActivity.runOnUiThread {
            lookupAdapter.updateData(listData)
            srlLookupData.isRefreshing = false
        }
    }

    override fun showError(tag: String, e: Exception) {
        runOnUiThread {
            AppEventLogging.logExceptionOnToast(tag,this@LookupActivity, e)
            srlLookupData.isRefreshing = false
        }
    }


}