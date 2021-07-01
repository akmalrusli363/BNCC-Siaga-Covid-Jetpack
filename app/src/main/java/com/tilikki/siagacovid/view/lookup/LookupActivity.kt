package com.tilikki.siagacovid.view.lookup

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tilikki.siagacovid.R
import com.tilikki.siagacovid.databinding.ActivityLookupBinding
import com.tilikki.siagacovid.model.RegionLookupData
import com.tilikki.siagacovid.utils.AppEventLogging
import com.tilikki.siagacovid.utils.SearchQueryTextListener
import com.tilikki.siagacovid.utils.StringParser
import com.tilikki.siagacovid.view.lookup.netmodel.RegionData
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
        setupSearchSorting()
        startObserve()
    }

    private val lookupAdapter = LookupAdapter()

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
        binding.svRegionLookupSearch.setOnQueryTextListener(
            SearchQueryTextListener(lookupAdapter)
        )

        binding.srlLookupData.setOnRefreshListener {
            viewModel.fetchData()
        }
    }

    private fun startObserve() {
        viewModel.regionData.observe(this) {
            lookupAdapter.updateData(it.toMutableList())
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

    private fun setupSearchSorting() {
        val sortTypes: Array<LookupComparator> = arrayOf(
            LookupComparator("Positive Cases", LookupComparator.compareByPositivityRate),
            LookupComparator("Recovered Cases", LookupComparator.compareByRecoveryRate),
            LookupComparator("Death Cases", LookupComparator.compareByDeathRate),
            LookupComparator("Daily Positive Cases", LookupComparator.compareByDailyPositivityRate),
            LookupComparator("Daily Recovered Cases", LookupComparator.compareByDailyRecoveryRate),
            LookupComparator("Daily Death Cases", LookupComparator.compareByDailyDeathRate),
        )
        val adapter: ArrayAdapter<LookupComparator> =
            ArrayAdapter(this, R.layout.custom_spinner_dropdown_item, sortTypes)

        binding.spRegionLookupSort.apply {
            this.adapter = adapter
            this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    i: Int,
                    l: Long
                ) {
                    val selectedItem = adapter.getItem(i)
                    lookupAdapter.sortDataWith(selectedItem!!)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
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

    private fun outputDate(date: Date?): String {
        return if (date != null) {
            StringParser.formatShortDate(date)
        } else {
            "Not Available"
        }
    }
}
