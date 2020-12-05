package com.tilikki.bnccapp.siagacovid.lookup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.tilikki.bnccapp.R
import kotlinx.android.synthetic.main.item_lookup.view.*

class LookupAdapter(private val lookupList: MutableList<LookupData>, private var dailyCaseVisibility: Boolean) :
    RecyclerView.Adapter<LookupViewHolder>(), Filterable {

    constructor(lookupList: MutableList<LookupData>) : this(lookupList, true)

    private var lookupListComparator: Comparator<LookupData> = LookupComparator.compareByPositivityRate
    private var filteredLookupList: MutableList<LookupData> = sortData(lookupList)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LookupViewHolder {
        return LookupViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_lookup, parent, false)
        )
    }

    override fun onBindViewHolder(holder: LookupViewHolder, position: Int) {
        if (dailyCaseVisibility) {
            holder.itemView.tvLookupDailyConfirmedCase.visibility = View.VISIBLE
            holder.itemView.tvLookupDailyRecoveredCase.visibility = View.VISIBLE
            holder.itemView.tvLookupDailyDeathCase.visibility = View.VISIBLE
        } else {
            holder.itemView.tvLookupDailyConfirmedCase.visibility = View.GONE
            holder.itemView.tvLookupDailyRecoveredCase.visibility = View.GONE
            holder.itemView.tvLookupDailyDeathCase.visibility = View.GONE
        }
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

    fun sortDataWith(lookupComparator: LookupComparator) {
        lookupListComparator = lookupComparator.comparator
        filteredLookupList = sortData(filteredLookupList, lookupListComparator)
        notifyDataSetChanged()
    }

    fun toggleDailyCaseVisibility(dailyCaseVisibility: Boolean) {
        this.dailyCaseVisibility = dailyCaseVisibility
        updateData()
    }

    fun updateData(newList: List<LookupData>) {
        lookupList.clear()
        lookupList.addAll(newList)
        updateData()
    }

    fun updateData() {
        filteredLookupList = sortData(lookupList)
        notifyDataSetChanged()
    }

    private fun sortData(list: MutableList<LookupData>?): MutableList<LookupData> {
        return sortData(list, lookupListComparator)
    }

    private fun sortData(list: MutableList<LookupData>?, comparator: Comparator<LookupData>): MutableList<LookupData> {
        return list?.sortedWith(comparator) as MutableList<LookupData>
    }

}