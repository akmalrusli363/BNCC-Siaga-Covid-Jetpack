package com.tilikki.bnccapp.siagacovid.worldstats

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.tilikki.bnccapp.R
import com.tilikki.bnccapp.databinding.ActivityWorldStatisticsBinding
import com.tilikki.bnccapp.siagacovid.PVContract
import com.tilikki.bnccapp.siagacovid.utils.AppEventLogging

class WorldStatisticsActivity : AppCompatActivity(), PVContract.View<WorldStatLookupData>,
    PVContract.ObjectView<WorldStatSummaryData> {
    private val presenter = WorldStatPresenter(WorldStatModel(), this, this)
    private lateinit var binding: ActivityWorldStatisticsBinding

    private var mockWorldLookupList: MutableList<WorldStatLookupData> = mutableListOf(
        WorldStatLookupData(
            "??", "Loading...",
            0, 0, 0,
            0, 0, 0
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_world_statistics)
        binding = ActivityWorldStatisticsBinding.inflate(layoutInflater)
        toggleFetchState(true)
        setupToolbar()
        setupRecyclerAdapter()
        setupReturnButton()
        setupSearch(worldStatAdapter)
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        title = ""
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.world_stat_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuItemRefresh -> {
                binding.bottomSheetCountryLookup.pbFetchLookup.visibility = View.VISIBLE
                toggleFetchState(true)
                fetchData()
            }
            R.id.submenuSortByCountryName -> {
                item.isChecked = !item.isChecked
                worldStatAdapter.setDataComparator(WorldStatDataComparator.COMPARE_BY_COUNTRY_NAME)
            }
            R.id.submenuSortByCountryCode -> {
                item.isChecked = !item.isChecked
                worldStatAdapter.setDataComparator(WorldStatDataComparator.COMPARE_BY_COUNTRY_CODE)
            }
            R.id.submenuSortByConfirmedCase -> {
                item.isChecked = !item.isChecked
                worldStatAdapter.setDataComparator(WorldStatDataComparator.COMPARE_BY_POSITIVITY_RATE)
            }
            R.id.submenuSortByDailyConfirmedCase -> {
                item.isChecked = !item.isChecked
                worldStatAdapter.setDataComparator(WorldStatDataComparator.COMPARE_BY_DAILY_POSITIVITY_RATE)
            }
            R.id.submenuSortByRecoveryCase -> {
                item.isChecked = !item.isChecked
                worldStatAdapter.setDataComparator(WorldStatDataComparator.COMPARE_BY_RECOVERY_RATE)
            }
            R.id.submenuSortByDailyRecoveryCase -> {
                item.isChecked = !item.isChecked
                worldStatAdapter.setDataComparator(WorldStatDataComparator.COMPARE_BY_DAILY_RECOVERY_RATE)
            }
            R.id.submenuSortByDeathCase -> {
                item.isChecked = !item.isChecked
                worldStatAdapter.setDataComparator(WorldStatDataComparator.COMPARE_BY_DEATH_RATE)
            }
            R.id.submenuSortByDailyDeathCase -> {
                item.isChecked = !item.isChecked
                worldStatAdapter.setDataComparator(WorldStatDataComparator.COMPARE_BY_DAILY_DEATH_RATE)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private val worldStatAdapter = WorldStatLookupAdapter(mockWorldLookupList)

    private fun setupRecyclerAdapter() {
        binding.bottomSheetCountryLookup.let {
            it.rvCountryLookupData.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            it.rvCountryLookupData.adapter = worldStatAdapter
        }
        fetchData()
    }

    private fun setupReturnButton() {
        binding.ivReturnIcon.setOnClickListener {
            finish()
        }
    }

    private fun setupSearch(worldStatLookupAdapter: WorldStatLookupAdapter) {
        binding.bottomSheetCountryLookup.svCountryLookupSearch.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    worldStatLookupAdapter.filter.filter(query)
                    return false
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    worldStatLookupAdapter.filter.filter(query)
                    return false
                }
            }
        )
    }

    private fun fetchData() {
        presenter.fetchData()
    }

    override fun updateData(listData: List<WorldStatLookupData>) {
        binding.bottomSheetCountryLookup.run {
            worldStatAdapter.updateData(listData)
            rvCountryLookupData.visibility = View.VISIBLE
            pbFetchLookup.visibility = View.GONE
            svCountryLookupSearch.setQuery(svCountryLookupSearch.query, true)
        }
    }

    override fun updateData(objectData: WorldStatSummaryData) {
        binding.widgetWorldCaseSummary.run {
            tvTotalCaseCount.text = "${objectData.numOfConfirmedCase}"
            tvRecoveredCount.text = "${objectData.numOfRecoveredCase}"
            tvDeathCount.text = "${objectData.numOfDeathCase}"
            tvPositiveCaseCount.text = "${objectData.numOfActiveCases()}"
            toggleFetchState(false)
        }
    }

    override fun showError(tag: String, e: Exception) {
        runOnUiThread {
            AppEventLogging.logExceptionOnToast(tag, this, e)
        }
    }

    private fun toggleFetchState(isFetching: Boolean) {
        binding.widgetWorldCaseSummary.run {
            toggleFetchState(isFetching, tvTotalCaseCount, pbTotalCase)
            toggleFetchState(isFetching, tvPositiveCaseCount, pbPositive)
            toggleFetchState(isFetching, tvRecoveredCount, pbRecovered)
            toggleFetchState(isFetching, tvDeathCount, pbDeath)
        }
    }

    private fun toggleFetchState(
        isFetching: Boolean,
        textView: TextView,
        progressBar: ProgressBar
    ) {
        if (isFetching) {
            textView.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        } else {
            textView.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }
}
