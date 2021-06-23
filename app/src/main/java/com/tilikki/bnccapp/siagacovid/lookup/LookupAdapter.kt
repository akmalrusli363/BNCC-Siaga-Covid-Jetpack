package com.tilikki.bnccapp.siagacovid.lookup

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.tilikki.bnccapp.databinding.ItemLookupBinding
import com.tilikki.bnccapp.siagacovid.model.RegionLookupData

class LookupAdapter :
    ListAdapter<RegionLookupData, LookupViewHolder>(LookupDiffCallback), Filterable {

    private var unfilteredData: List<RegionLookupData> = listOf()
    private var lookupListComparator: Comparator<RegionLookupData> =
        LookupComparator.compareByPositivityRate

    object LookupDiffCallback : DiffUtil.ItemCallback<RegionLookupData>() {
        override fun areItemsTheSame(
            oldItem: RegionLookupData,
            newItem: RegionLookupData
        ): Boolean {
            return oldItem.province == newItem.province
        }

        override fun areContentsTheSame(
            oldItem: RegionLookupData,
            newItem: RegionLookupData
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LookupViewHolder {
        return LookupViewHolder(
            ItemLookupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: LookupViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(keyword: CharSequence?): FilterResults {
                val query = keyword.toString()
                return FilterResults().apply {
                    values = sortData(filterRegion(query), lookupListComparator)
                }
            }

            override fun publishResults(keyword: CharSequence?, result: FilterResults?) {
                submitList(result?.values as MutableList<RegionLookupData>)
                notifyDataSetChanged()
            }

            private fun filterRegion(query: String): List<RegionLookupData> {
                return if (query.isBlank()) {
                    unfilteredData.toMutableList()
                } else {
                    unfilteredData.filter {
                        it.province.contains(query, true)
                    }
                }
            }
        }
    }

    fun updateData(list: MutableList<RegionLookupData>) {
        unfilteredData = list
        submitList(list)
    }

    fun sortDataWith(lookupComparator: LookupComparator) {
        lookupListComparator = lookupComparator.comparator
        submitList(sortData(currentList, lookupListComparator))
    }

    private fun sortData(
        list: List<RegionLookupData>,
        comparator: Comparator<RegionLookupData>
    ): MutableList<RegionLookupData> {
        return list.sortedWith(comparator).toMutableList()
    }

}
