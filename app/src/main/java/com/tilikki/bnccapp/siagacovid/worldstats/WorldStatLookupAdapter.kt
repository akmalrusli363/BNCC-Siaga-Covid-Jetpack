package com.tilikki.bnccapp.siagacovid.worldstats

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.tilikki.bnccapp.R

class WorldStatLookupAdapter(private val worldStatLookupData: MutableList<WorldStatLookupData>) :
    RecyclerView.Adapter<WorldStatLookupViewHolder>(), Filterable {

    var filteredWorldStatLookupData: MutableList<WorldStatLookupData> = worldStatLookupData
    private var worldStatLookupDataComparator: Comparator<WorldStatLookupData> = WorldStatDataComparator.COMPARE_BY_POSITIVITY_RATE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorldStatLookupViewHolder {
        return WorldStatLookupViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_country_lookup, parent, false)
        )
    }

    override fun onBindViewHolder(holderLookup: WorldStatLookupViewHolder, position: Int) {
        holderLookup.bind(filteredWorldStatLookupData[position])
    }

    override fun getItemCount(): Int {
        return filteredWorldStatLookupData.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(keyword: CharSequence?): FilterResults {
                val query = keyword.toString()
                filteredWorldStatLookupData = if (query.isEmpty()) {
                    worldStatLookupData
                } else {
                    worldStatLookupData.filter {
                        it.countryName.contains(query, true)
                    } as MutableList<WorldStatLookupData>
                }
                return FilterResults().also {
                    filteredWorldStatLookupData = sortData(filteredWorldStatLookupData)
                    it.values = filteredWorldStatLookupData
                }
            }

            override fun publishResults(keyword: CharSequence?, result: FilterResults?) {
                notifyDataSetChanged()
            }
        }
    }

    fun updateData(newList: List<WorldStatLookupData>) {
        worldStatLookupData.clear()
        worldStatLookupData.addAll(newList)
        notifyDataSetChanged()
        filteredWorldStatLookupData = sortData(worldStatLookupData)
    }

    private fun sortData(list: MutableList<WorldStatLookupData>): MutableList<WorldStatLookupData> {
        return sortData(list, worldStatLookupDataComparator)
    }

    private fun sortData(list: MutableList<WorldStatLookupData>?, comparator: Comparator<WorldStatLookupData>): MutableList<WorldStatLookupData> {
        return list?.sortedWith(comparator) as MutableList<WorldStatLookupData>
    }

    private fun sortByPositivityRate(list: MutableList<WorldStatLookupData>): MutableList<WorldStatLookupData> {
        return list.sortedByDescending { worldStatLookupData: WorldStatLookupData ->
            worldStatLookupData.numOfConfirmedCase
        } as MutableList<WorldStatLookupData>
    }

}