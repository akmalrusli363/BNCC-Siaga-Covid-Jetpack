package com.tilikki.bnccapp.siagacovid.lookup

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tilikki.bnccapp.R
import com.tilikki.bnccapp.siagacovid.PVContract
import com.tilikki.bnccapp.siagacovid.utils.AppEventLogging
import kotlinx.android.synthetic.main.activity_lookup.*

class LookupActivity : AppCompatActivity(), PVContract.View<LookupData> {
    private val presenter = LookupPresenter(LookupModel(), this)

    private var isRefreshing = true

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
        svRegionLookupSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }


            override fun onQueryTextChange(query: String?): Boolean {
                lookupAdapter.filter.filter(query)
                return false
            }
        })

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
            pbFetchLookup.visibility = View.GONE
            rvLookUp.visibility = View.VISIBLE
        }
    }

    override fun showError(tag: String, e: Exception) {
        runOnUiThread {
            AppEventLogging.logExceptionOnToast(tag, this@LookupActivity, e)
            srlLookupData.isRefreshing = false
        }
    }
}