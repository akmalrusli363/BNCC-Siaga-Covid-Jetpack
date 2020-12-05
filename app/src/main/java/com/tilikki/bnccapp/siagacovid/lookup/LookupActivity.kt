package com.tilikki.bnccapp.siagacovid.lookup

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tilikki.bnccapp.R
import com.tilikki.bnccapp.siagacovid.PVContract
import com.tilikki.bnccapp.siagacovid.utils.AppEventLogging
import com.tilikki.bnccapp.siagacovid.utils.StringParser
import kotlinx.android.synthetic.main.activity_lookup.*
import java.util.*

class LookupActivity : AppCompatActivity(), PVContract.ObjectView<LookupSummaryData> {
    private var presenter = LookupPresenter(GovernmentLookupModel(), this)

    private var mockLookupList: MutableList<LookupData> = mutableListOf(
        LookupData("Loading...", 0, 0, 0)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lookup)
        setupRecyclerAdapter()
        setupReturnButton()
        setupSearch(lookupAdapter)
        setupSearchSorting()
        setupToggleButton()
    }

    private val lookupAdapter = LookupAdapter(mockLookupList)

    private fun setupRecyclerAdapter() {
        rvLookupData.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvLookupData.adapter = lookupAdapter
        fetchData()
    }

    private fun setupReturnButton() {
        ivReturnIcon.setOnClickListener {
            finish()
        }
    }

    private fun setupToggleButton() {
        tbDataProviderSwitch.setOnClickListener {
            val fromGovernment = !tbDataProviderSwitch.isChecked
            setSearchSort(fromGovernment)

            val lookupModel = if (fromGovernment) {
                GovernmentLookupModel()
            } else {
                ArcGISLookupModel()
            }
            presenter = LookupPresenter(lookupModel, this)
            fetchData()

            lookupAdapter.toggleDailyCaseVisibility(fromGovernment)
            lookupAdapter.notifyDataSetChanged()
        }
    }

    private fun setupSearch(lookupAdapter: LookupAdapter) {
        svRegionLookupSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                lookupAdapter.filter.filter(query)
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

    private fun setSearchSort(dailyCaseVisibility: Boolean) {
        val sortTypes: List<LookupComparator> = CaseDataSorter.getSortTypes(dailyCaseVisibility)
        val adapter: ArrayAdapter<LookupComparator> =
            ArrayAdapter(this, R.layout.custom_spinner_dropdown_item, sortTypes)
        spRegionLookupSort.adapter = adapter
    }

    private fun setupSearchSorting() {
        val sortTypes: List<LookupComparator> =
            CaseDataSorter.getSortTypes(!tbDataProviderSwitch.isChecked)

        val adapter: ArrayAdapter<LookupComparator> =
            ArrayAdapter(this, R.layout.custom_spinner_dropdown_item, sortTypes)

        spRegionLookupSort.apply {
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

    override fun updateData(objectData: LookupSummaryData) {
        this@LookupActivity.runOnUiThread {
            lookupAdapter.updateData(objectData.lookupData)
            tvLastUpdated.text = getDataSourceInformation(objectData)
            srlLookupData.isRefreshing = false
            pbFetchLookup.visibility = View.GONE
            rvLookupData.visibility = View.VISIBLE
            svRegionLookupSearch.setQuery(svRegionLookupSearch.query, true)
        }
    }

    override fun showError(tag: String, e: Exception) {
        runOnUiThread {
            AppEventLogging.logExceptionOnToast(tag, this@LookupActivity, e)
            srlLookupData.isRefreshing = false
        }
    }

    private fun getDataSourceInformation(objectData: LookupSummaryData): String {
        return if (objectData.lastUpdated != null)
            String.format(
                Locale.ROOT, "%s | Updated: %s",
                objectData.provider, outputDate(objectData.lastUpdated)
            )
        else
            String.format(
                Locale.ROOT, "%s",
                objectData.provider
            )
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