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
import kotlinx.android.synthetic.main.activity_lookup.*

class LookupActivity : AppCompatActivity(), PVContract.View<LookupData> {
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

    private fun updateSearchSort(dailyCaseVisibility: Boolean) {
        val sortTypes: MutableList<LookupComparator> = CaseDataSorter.sortTypes

        if (dailyCaseVisibility) {
            sortTypes.addAll(CaseDataSorter.dailySortTypes)
        }
        
    }

    private fun setupSearchSorting() {
        val sortTypes: MutableList<LookupComparator> = CaseDataSorter.sortTypes

        if (true) {
            sortTypes.addAll(CaseDataSorter.dailySortTypes)
        }

        val adapter: ArrayAdapter<LookupComparator> =
            ArrayAdapter(this, R.layout.custom_spinner_dropdown_item, sortTypes)

        spRegionLookupSort.apply {
            this.adapter = adapter
            this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, i: Int, l: Long) {
                    val selectedItem = adapter.getItem(i)
                    lookupAdapter.sortDataWith(selectedItem!!)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }
        }
    }

    override fun updateData(listData: List<LookupData>) {
        this@LookupActivity.runOnUiThread {
            lookupAdapter.updateData(listData)
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

    private object CaseDataSorter {
        val sortTypes: MutableList<LookupComparator> = mutableListOf(
            LookupComparator("Positive Cases", LookupComparator.compareByPositivityRate),
            LookupComparator("Recovered Cases", LookupComparator.compareByRecoveryRate),
            LookupComparator("Death Cases", LookupComparator.compareByDeathRate),
        )
        val dailySortTypes: List<LookupComparator> = listOf(
            LookupComparator("Daily Positive Cases", LookupComparator.compareByDailyPositivityRate),
            LookupComparator("Daily Recovered Cases", LookupComparator.compareByDailyRecoveryRate),
            LookupComparator("Daily Death Cases", LookupComparator.compareByDailyDeathRate),
        )
    }
}