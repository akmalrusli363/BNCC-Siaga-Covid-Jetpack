package com.tilikki.bnccapp.siagacovid.lookup

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tilikki.bnccapp.R
import com.tilikki.bnccapp.databinding.ActivityLookupBinding
import com.tilikki.bnccapp.siagacovid.lookup.netmodel.RegionData
import com.tilikki.bnccapp.siagacovid.model.RegionLookupData
import com.tilikki.bnccapp.siagacovid.utils.AppEventLogging
import com.tilikki.bnccapp.siagacovid.utils.StringParser
import java.util.*

class LookupActivity : AppCompatActivity() {
    private lateinit var viewModel: LookupViewModel
    private lateinit var binding: ActivityLookupBinding

    private val mockLookupList: MutableList<RegionLookupData> = mutableListOf(
        RegionData("Loading...").toRegionLookupData()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLookupBinding.inflate(layoutInflater)
        viewModel = LookupViewModel()
        setContentView(binding.root)
        setupRecyclerAdapter()
        setupReturnButton()
        setupSearch(lookupAdapter)
        viewModel.fetchData()
        startObserve()
    }

    private val lookupAdapter = LookupAdapter(mockLookupList)

    private fun setupRecyclerAdapter() {
        binding.rvLookupData.let {
            it.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            it.adapter = lookupAdapter
        }
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
            viewModel.fetchData()
        }
    }

    private fun startObserve() {
        viewModel.regionData.observe(this) {
            lookupAdapter.updateData(it)
        }
        viewModel.lastUpdated.observe(this) {
            binding.tvLastUpdated.text = getString(R.string.last_updated)
                .replace("???", outputDate(it))
        }
        viewModel.successResponse.observe(this) {
            if (!it.success && it.error != null) {
                showError(AppEventLogging.FETCH_FAILURE, it.error as Exception)
            } else {
                binding.apply {
                    srlLookupData.isRefreshing = false
                    pbFetchLookup.visibility = View.GONE
                    rvLookupData.visibility = View.VISIBLE
                    svRegionLookupSearch.apply {
                        setQuery(query, true)
                    }
                }
            }
        }
    }

    private fun showError(tag: String, e: Exception) {
        runOnUiThread {
            AppEventLogging.logExceptionOnToast(tag, this@LookupActivity, e)
            binding.srlLookupData.isRefreshing = false
        }
    }

    private fun outputDate(date: Date?) :String {
        return if (date != null) {
            StringParser.formatShortDate(date)
        } else {
            "Not Available"
        }
    }
}
