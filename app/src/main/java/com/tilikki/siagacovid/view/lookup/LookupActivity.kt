package com.tilikki.siagacovid.view.lookup

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tilikki.siagacovid.R
import com.tilikki.siagacovid.databinding.ActivityLookupBinding
import com.tilikki.siagacovid.utils.AppEventLogging
import com.tilikki.siagacovid.utils.SearchQueryTextListener
import com.tilikki.siagacovid.utils.StringParser
import com.tilikki.siagacovid.utils.ViewUtility
import java.util.*

class LookupActivity : AppCompatActivity() {
    private lateinit var viewModel: LookupViewModel
    private lateinit var binding: ActivityLookupBinding
    private lateinit var lookupAdapter: LookupAdapter

    private val dailyCaseVisibility = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLookupBinding.inflate(layoutInflater).also {
            viewModel = LookupViewModel()
            lookupAdapter = LookupAdapter(dailyCaseVisibility)
        }
        setContentView(binding.root)
        setupComponents()
        setupRecyclerAdapter()
        setupSearch(lookupAdapter)
        setupSearchSorting()
        startObserve()
    }

    private fun setupRecyclerAdapter() {
        binding.rvLookupData.let {
            it.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            it.adapter = lookupAdapter
        }
    }

    private fun setupComponents() {
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
            binding.tvLastUpdated.text = getDataSourceInformation()
        }
        viewModel.successResponse.observe(this) {
            if (!it.success && it.error != null) {
                showError(AppEventLogging.FETCH_FAILURE, it.error as Exception)
            } else {
                binding.apply {
                    srlLookupData.isRefreshing = false
                    toggleLoadingState(false)
                    svRegionLookupSearch.apply {
                        setQuery(query, true)
                    }
                }
            }
        }
        lookupAdapter.toggleDailyCaseVisibility(dailyCaseVisibility)
        setSearchSort(dailyCaseVisibility)
        viewModel.fetchData()
        toggleLoadingState(true)
    }

    private fun getDataSourceInformation(): String {
        viewModel.run {
            return if (lastUpdated.value != null)
                String.format(
                    Locale.ROOT, "%s | Updated: %s",
                    dataSource.value, outputDate(lastUpdated.value)
                )
            else
                String.format(Locale.ROOT, "%s", dataSource.value)
        }
    }

    private fun setSearchSort(dailyCaseVisibility: Boolean) {
        val sortTypes: List<LookupComparator> = CaseDataSorter.getSortTypes(dailyCaseVisibility)
        val adapter: ArrayAdapter<LookupComparator> =
            ArrayAdapter(this, R.layout.custom_spinner_dropdown_item, sortTypes)
        binding.spRegionLookupSort.adapter = adapter
    }

    private fun setupSearchSorting() {
        val sortTypes: List<LookupComparator> = CaseDataSorter.getSortTypes(dailyCaseVisibility)

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

    private fun toggleLoadingState(loading: Boolean) {
        ViewUtility.setVisibility(binding.pbFetchLookup, loading)
        ViewUtility.setVisibility(binding.rvLookupData, !loading)
    }

    private object CaseDataSorter {
        val sortTypes: List<LookupComparator> = listOf(
            LookupComparator("Positive Cases", LookupComparator.compareByPositivityRate),
            LookupComparator("Recovered Cases", LookupComparator.compareByRecoveryRate),
            LookupComparator("Death Cases", LookupComparator.compareByDeathRate),
        )
        val dailySortTypes: List<LookupComparator> = listOf(
            LookupComparator("Daily Positive Cases", LookupComparator.compareByDailyPositivityRate),
            LookupComparator("Daily Recovered Cases", LookupComparator.compareByDailyRecoveryRate),
            LookupComparator("Daily Death Cases", LookupComparator.compareByDailyDeathRate),
        )

        fun getSortTypes(dailyCaseVisibility: Boolean): List<LookupComparator> {
            val sortTypeList = sortTypes.toMutableList()
            if (dailyCaseVisibility) sortTypeList.addAll(dailySortTypes)
            return sortTypeList.toList()
        }
    }
}
