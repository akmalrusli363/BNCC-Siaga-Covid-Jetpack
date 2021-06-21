package com.tilikki.bnccapp.siagacovid.lookup

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tilikki.bnccapp.R
import com.tilikki.bnccapp.databinding.ActivityLookupBinding
import com.tilikki.bnccapp.siagacovid.PVContract
import com.tilikki.bnccapp.siagacovid.utils.AppEventLogging
import com.tilikki.bnccapp.siagacovid.utils.StringParser
import java.util.*

class LookupActivity : AppCompatActivity(), PVContract.ObjectView<LookupSummaryData> {
    private val presenter = LookupPresenter(LookupModel(), this)

    private lateinit var binding: ActivityLookupBinding

    private var mockLookupList: MutableList<LookupData> = mutableListOf(
        LookupData("Loading...", 0, 0, 0)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLookupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerAdapter()
        setupReturnButton()
        setupSearch(lookupAdapter)
    }

    override fun updateData(objectData: LookupSummaryData) {
        this@LookupActivity.runOnUiThread {
            lookupAdapter.updateData(objectData.lookupData)
            binding.apply {
                tvLastUpdated.text = getString(R.string.last_updated)
                    .replace("???", outputDate(objectData.lastUpdated))

                srlLookupData.isRefreshing = false
                pbFetchLookup.visibility = View.GONE
                rvLookupData.visibility = View.VISIBLE
                svRegionLookupSearch.apply {
                    setQuery(query, true)
                }
            }
        }
    }

    override fun showError(tag: String, e: Exception) {
        runOnUiThread {
            AppEventLogging.logExceptionOnToast(tag, this@LookupActivity, e)
            binding.srlLookupData.isRefreshing = false
        }
    }

    private val lookupAdapter = LookupAdapter(mockLookupList)

    private fun setupRecyclerAdapter() {
        binding.rvLookupData.let {
            it.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            it.adapter = lookupAdapter
        }
        fetchData()
    }

    private fun setupReturnButton() {
        binding.ivReturnIcon.setOnClickListener {
            finish()
        }
    }
    private fun setupSearch(lookupAdapter: LookupAdapter) {
        binding.svRegionLookupSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                lookupAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                lookupAdapter.filter.filter(query)
                return false
            }
        })

        binding.srlLookupData.setOnRefreshListener {
            fetchData()
        }
    }

    private fun fetchData() {
        presenter.fetchData()
    }

    private fun outputDate(date: Date?) :String {
        return if (date != null) {
            StringParser.formatShortDate(date)
        } else {
            "Not Available"
        }
    }
}