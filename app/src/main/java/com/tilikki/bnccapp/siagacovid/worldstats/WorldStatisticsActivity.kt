package com.tilikki.bnccapp.siagacovid.worldstats

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tilikki.bnccapp.R
import com.tilikki.bnccapp.siagacovid.PVContract
import com.tilikki.bnccapp.siagacovid.utils.AppEventLogging
import kotlinx.android.synthetic.main.activity_world_statistics.*
import kotlinx.android.synthetic.main.bottom_sheet_country_lookup.*

class WorldStatisticsActivity : AppCompatActivity(), PVContract.View<WorldStatLookupData>, PVContract.ObjectView<WorldStatSummaryData> {
    private val presenter = WorldStatPresenter(WorldStatModel(), this, this)

    private var mockWorldLookupList: MutableList<WorldStatLookupData> = mutableListOf(
        WorldStatLookupData("??", "Loading...",
            0, 0, 0,
            0, 0, 0)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_world_statistics)
        toggleFetchState(true)
        setupRecyclerAdapter()
        setupReturnButton()
        setupSearch(worldStatAdapter)
    }

    private val worldStatAdapter = WorldStatAdapter(mockWorldLookupList)

    private fun setupRecyclerAdapter() {
        rvCountryLookupData.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvCountryLookupData.adapter = worldStatAdapter
        fetchData()
    }

    private fun setupReturnButton() {
        ivReturnIcon.setOnClickListener {
            finish()
        }
    }

    private fun setupSearch(worldStatLookupAdapter: WorldStatLookupAdapter) {
        svCountryLookupSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                worldStatLookupAdapter.filter.filter(query)
                return false
            }
        })
    }

    private fun fetchData() {
        presenter.fetchData()
    }

    override fun updateData(listData: List<WorldStatLookupData>) {
        runOnUiThread {
            worldStatAdapter.updateData(listData)
            rvCountryLookupData.visibility = View.VISIBLE
            pbFetchLookup.visibility = View.GONE
            srlWorldStats.isRefreshing = false
        }
    }

    override fun updateData(objectData: WorldStatSummaryData) {
        runOnUiThread {
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
        toggleFetchState(isFetching, tvTotalCaseCount, pbTotalCase)
        toggleFetchState(isFetching, tvPositiveCaseCount, pbPositive)
        toggleFetchState(isFetching, tvRecoveredCount, pbRecovered)
        toggleFetchState(isFetching, tvDeathCount, pbDeath)
    }

    private fun toggleFetchState(isFetching: Boolean, textView: TextView, progressBar: ProgressBar) {
        if (isFetching) {
            textView.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        } else {
            textView.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }
}