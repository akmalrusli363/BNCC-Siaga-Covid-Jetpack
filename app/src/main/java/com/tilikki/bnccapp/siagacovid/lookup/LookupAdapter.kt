package com.tilikki.bnccapp.siagacovid.lookup

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.tilikki.bnccapp.R

class LookupAdapter(private val lookupList: MutableList<LookupData>) :
    RecyclerView.Adapter<LookupViewHolder>(), Filterable {

    private var lookupListComparator: Comparator<LookupData> = LookupComparator.compareByPositivityRate
    private var filteredLookupList: MutableList<LookupData> = sortData(lookupList)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LookupViewHolder {
        return LookupViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_lookup, parent, false)
        )
    }

    override fun onBindViewHolder(holder: LookupViewHolder, position: Int) {
        holder.bind(filteredLookupList[position])
    }

    override fun getItemCount(): Int {
        return filteredLookupList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(keyword: CharSequence?): FilterResults {
                val query = keyword.toString()
                filteredLookupList = if (query.isEmpty()) {
                    lookupList
                } else {
                    lookupList.filter {
                        it.provinceName.contains(query, true)
                    } as MutableList<LookupData>
                }
                return FilterResults().also {
                    filteredLookupList = sortData(filteredLookupList)
                    it.values = filteredLookupList
                }
            }

            override fun publishResults(keyword: CharSequence?, result: FilterResults?) {
                notifyDataSetChanged()
            }
        }
    }

    fun updateData(newList: List<LookupData>) {
        lookupList.clear()
        lookupList.addAll(newList)
        notifyDataSetChanged()
        sortData(lookupList)
        filteredLookupList = lookupList
    }

    fun sortDataWith(lookupComparator: LookupComparator) {
        lookupListComparator = lookupComparator.comparator
        filteredLookupList = sortData(filteredLookupList, lookupListComparator)
        notifyDataSetChanged()
    }

    private fun sortData(list: MutableList<LookupData>?): MutableList<LookupData> {
        return sortData(list, lookupListComparator)
    }

    private fun sortData(list: MutableList<LookupData>?, comparator: Comparator<LookupData>): MutableList<LookupData> {
        return list?.sortedWith(comparator) as MutableList<LookupData>
    }

}