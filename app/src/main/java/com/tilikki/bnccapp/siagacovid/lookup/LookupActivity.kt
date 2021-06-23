package com.tilikki.bnccapp.siagacovid.lookup

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tilikki.bnccapp.R
import com.tilikki.bnccapp.databinding.ActivityLookupBinding
import com.tilikki.bnccapp.siagacovid.utils.AppEventLogging
import com.tilikki.bnccapp.siagacovid.utils.StringParser
import java.util.*

class LookupActivity : AppCompatActivity() {
    private lateinit var viewModel: LookupViewModel
    private lateinit var binding: ActivityLookupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLookupBinding.inflate(layoutInflater)
        viewModel = LookupViewModel()
        setContentView(binding.root)
        setupComponents()
        setupRecyclerAdapter()
        setupSearch(lookupAdapter)
        viewModel.fetchData()
        setupSearchSorting()
        startObserve()
    }

    private lateinit var lookupAdapter: LookupAdapter

    private fun setupRecyclerAdapter() {
        binding.rvLookupData.let {
            it.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            it.adapter = LookupAdapter(providedFromGovernment())
        }
    }

    private fun providedFromGovernment(): Boolean {
        return !binding.tbDataProviderSwitch.isSelected
    }

    private fun setupComponents() {
        binding.ivReturnIcon.setOnClickListener {
            finish()
        }
        binding.tbDataProviderSwitch.setOnClickListener {
            viewModel.toggleDataSource(providedFromGovernment())
        }
    }

    private fun setupSearch(lookupAdapter: LookupAdapter) {
        binding.svRegionLookupSearch
            .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
        viewModel.fromGovernment.observe(this) {
            lookupAdapter.toggleDailyCaseVisibility(it)
            setSearchSort(it)
            viewModel.fetchData()
        }
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
        val sortTypes: List<LookupComparator> =
            CaseDataSorter.getSortTypes(viewModel.fromGovernment.value ?: true)

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
