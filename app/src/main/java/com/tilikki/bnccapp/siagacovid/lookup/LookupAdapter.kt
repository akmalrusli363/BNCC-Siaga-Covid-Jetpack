package com.tilikki.bnccapp.siagacovid.lookup

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.tilikki.bnccapp.databinding.ItemLookupBinding
import com.tilikki.bnccapp.siagacovid.lookup.netmodel.RegionData

class LookupAdapter(private val regionData: MutableList<RegionData>) :
    RecyclerView.Adapter<LookupViewHolder>(), Filterable {

    var filteredRegionData: MutableList<RegionData> = sortData(regionData)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LookupViewHolder {
        return LookupViewHolder(
            ItemLookupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: LookupViewHolder, position: Int) {
        holder.bind(filteredRegionData[position])
    }

    override fun getItemCount(): Int {
        return filteredRegionData.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(keyword: CharSequence?): FilterResults {
                val query = keyword.toString()
                filteredRegionData = if (query.isEmpty()) {
                    regionData
                } else {
                    regionData.filter {
                        it.province.contains(query, true)
                    } as MutableList<RegionData>
                }
                return FilterResults().also {
                    filteredRegionData = sortData(filteredRegionData)
                    it.values = filteredRegionData
                }
            }

            override fun publishResults(keyword: CharSequence?, result: FilterResults?) {
                notifyDataSetChanged()
            }
        }
    }

    fun updateData(newList: List<RegionData>) {
        regionData.clear()
        regionData.addAll(newList)
        notifyDataSetChanged()
        filteredRegionData = regionData
    }

    private fun sortData(list: MutableList<RegionData>): MutableList<RegionData> {
        return sortByPositivityRate(list)
    }

    private fun sortByPositivityRate(list: MutableList<RegionData>): MutableList<RegionData> {
        return list.sortedByDescending {
            it.totalConfirmedCase
        } as MutableList<RegionData>
    }

}
