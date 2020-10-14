package com.tilikki.bnccapp.siagacovid.worldstats

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tilikki.bnccapp.R
import com.tilikki.bnccapp.siagacovid.PVContract
import com.tilikki.bnccapp.siagacovid.utils.AppEventLogging
import kotlinx.android.synthetic.main.activity_world_statistics.*
import kotlinx.android.synthetic.main.bottom_sheet_country_lookup.*

class WorldStatisticsActivity : AppCompatActivity(), PVContract.View<WorldStatData> {
    private val presenter = WorldStatPresenter(WorldStatModel(), this)

    private var mockWorldLookupList: MutableList<WorldStatData> = mutableListOf(
        WorldStatData("??", "Loading...",
            0, 0, 0,
            0, 0, 0)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_world_statistics)
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

    private fun setupSearch(worldStatAdapter: WorldStatAdapter) {
        svCountryLookupSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                worldStatAdapter.filter.filter(query)
                return false
            }
        })
    }

    private fun fetchData() {
        presenter.fetchData()
    }

    override fun updateData(listData: List<WorldStatData>) {
        runOnUiThread {
            worldStatAdapter.updateData(listData)
            rvCountryLookupData.visibility = View.VISIBLE
            pbFetchLookup.visibility = View.GONE
        }
    }

    override fun showError(tag: String, e: Exception) {
        runOnUiThread {
            AppEventLogging.logExceptionOnToast(tag, this, e)
        }
    }
}