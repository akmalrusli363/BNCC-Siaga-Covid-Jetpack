package com.tilikki.bnccapp.siagacovid.view.worldstats

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.tilikki.bnccapp.R
import com.tilikki.bnccapp.databinding.ActivityWorldStatisticsBinding
import com.tilikki.bnccapp.siagacovid.model.CountryLookupData
import com.tilikki.bnccapp.siagacovid.model.WorldCaseOverview
import com.tilikki.bnccapp.siagacovid.utils.AppEventLogging
import com.tilikki.bnccapp.siagacovid.utils.SearchQueryTextListener
import com.tilikki.bnccapp.siagacovid.utils.StringParser
import com.tilikki.bnccapp.siagacovid.utils.ViewUtility

class WorldStatisticsActivity : AppCompatActivity() {
    private val viewModel = WorldStatViewModel()
    private lateinit var binding: ActivityWorldStatisticsBinding

    private val worldStatAdapter = WorldStatLookupAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorldStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupComponents()
        setupRecyclerAdapter()
        setupSearch(worldStatAdapter)
        startObserve()
    }

    private fun setupComponents() {
        binding.ivReturnIcon.setOnClickListener {
            finish()
        }
        setupToolbar()
        toggleFetchState(true)
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
                viewModel.fetchData()
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

    private fun setupRecyclerAdapter() {
        binding.bottomSheetCountryLookup.let {
            it.rvCountryLookupData.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            it.rvCountryLookupData.adapter = worldStatAdapter
        }
        viewModel.fetchData()
    }

    private fun setupSearch(worldStatLookupAdapter: WorldStatLookupAdapter) {
        binding.bottomSheetCountryLookup.svCountryLookupSearch.setOnQueryTextListener(
            SearchQueryTextListener(worldStatLookupAdapter)
        )
    }

    private fun startObserve() {
        viewModel.worldCaseOverview.observe(this) {
            updateGlobalData(it)
        }
        viewModel.countryLookupData.observe(this) {
            updateCountryData(it)
        }
        viewModel.dataSource.observe(this) {
            binding.widgetWorldCaseSummary.run {
                tvProvidedData.text = it
            }
        }
        viewModel.successResponse.observe(this) {
            if (!it.success && it.error != null) {
                showError(AppEventLogging.FETCH_FAILURE, it.error as Exception)
            } else {
                toggleFetchState(false)
            }
        }
    }

    private fun updateCountryData(listData: List<CountryLookupData>) {
        binding.bottomSheetCountryLookup.run {
            worldStatAdapter.updateData(listData)
            svCountryLookupSearch.setQuery(svCountryLookupSearch.query, true)
        }
    }

    private fun updateGlobalData(objectData: WorldCaseOverview) {
        binding.widgetWorldCaseSummary.run {
            tvTotalCaseCount.text = "${objectData.confirmedCase}"
            tvRecoveredCount.text = "${objectData.recoveredCase}"
            tvDeathCount.text = "${objectData.deathCase}"
            tvActiveCaseCount.text = "${objectData.activeCases()}"
            tvLastUpdated.text = getString(R.string.last_updated)
                .replace("???", StringParser.formatDate(objectData.lastUpdated))
        }
    }

    private fun showError(tag: String, e: Exception) {
        runOnUiThread {
            AppEventLogging.logExceptionOnToast(tag, this, e)
        }
    }

    private fun toggleFetchState(isFetching: Boolean) {
        binding.run {
            widgetWorldCaseSummary.run {
                toggleFetchState(isFetching, tvTotalCaseCount, pbTotalCase)
                toggleFetchState(isFetching, tvActiveCaseCount, pbActiveCase)
                toggleFetchState(isFetching, tvRecoveredCount, pbRecovered)
                toggleFetchState(isFetching, tvDeathCount, pbDeath)
                ViewUtility.setVisibility(tvProvidedData, !isFetching)
                ViewUtility.setVisibility(tvLastUpdated, !isFetching)
            }
            binding.bottomSheetCountryLookup.run {
                toggleFetchState(isFetching, rvCountryLookupData, pbFetchLookup)
            }
        }
    }

    private fun toggleFetchState(
        isFetching: Boolean,
        view: View,
        progressBar: ProgressBar
    ) {
        ViewUtility.setVisibility(view, !isFetching)
        ViewUtility.setVisibility(progressBar, isFetching)
    }
}
