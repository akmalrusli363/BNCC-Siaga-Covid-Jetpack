package com.tilikki.bnccapp.siagacovid.worldstats

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.tilikki.bnccapp.R

class WorldStatAdapter(private val worldStatData: MutableList<WorldStatData>) :
    RecyclerView.Adapter<WorldStatViewHolder>(), Filterable {

    var filteredWorldStatData: MutableList<WorldStatData> = sortData(worldStatData)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorldStatViewHolder {
        return WorldStatViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_country_lookup, parent, false)
        )
    }

    override fun onBindViewHolder(holder: WorldStatViewHolder, position: Int) {
        holder.bind(filteredWorldStatData[position])
    }

    override fun getItemCount(): Int {
        return filteredWorldStatData.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(keyword: CharSequence?): FilterResults {
                val query = keyword.toString()
                filteredWorldStatData = if (query.isEmpty()) {
                    worldStatData
                } else {
                    worldStatData.filter {
                        it.countryName.contains(query, true)
                    } as MutableList<WorldStatData>
                }
                return FilterResults().also {
                    filteredWorldStatData = sortData(filteredWorldStatData)
                    it.values = filteredWorldStatData
                }
            }

            override fun publishResults(keyword: CharSequence?, result: FilterResults?) {
                notifyDataSetChanged()
            }
        }
    }

    fun updateData(newList: List<WorldStatData>) {
        worldStatData.clear()
        worldStatData.addAll(newList)
        notifyDataSetChanged()
        filteredWorldStatData = worldStatData
    }

    private fun sortData(list: MutableList<WorldStatData>): MutableList<WorldStatData> {
        return sortByPositivityRate(list)
    }

    private fun sortByPositivityRate(list: MutableList<WorldStatData>): MutableList<WorldStatData> {
        return list.sortedByDescending { worldStatData: WorldStatData ->
            worldStatData.numOfConfirmedCase
        } as MutableList<WorldStatData>
    }

}