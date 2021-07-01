package com.tilikki.siagacovid.utils

import android.widget.Filterable
import android.widget.SearchView
import androidx.recyclerview.widget.RecyclerView

class SearchQueryTextListener<in T>(private val rvAdapter: T) : SearchView.OnQueryTextListener
        where T : RecyclerView.Adapter<*>, T : Filterable {

    override fun onQueryTextSubmit(query: String?): Boolean {
        rvAdapter.filter.filter(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        rvAdapter.filter.filter(newText)
        return false
    }
}
