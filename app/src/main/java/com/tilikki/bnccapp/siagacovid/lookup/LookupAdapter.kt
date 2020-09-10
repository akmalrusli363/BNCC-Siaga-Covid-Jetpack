package com.tilikki.bnccapp.siagacovid.lookup

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.tilikki.bnccapp.R
import java.util.*

class LookupAdapter(private val lookupList: MutableList<LookupData>) :
    RecyclerView.Adapter<LookupViewHolder>(), Filterable {

    var filteredLookupList: MutableList<LookupData> = sortByPositivityRate(lookupList)

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
                    filteredLookupList = sortByPositivityRate(filteredLookupList)
                    it.values = filteredLookupList
                }
            }

            override fun publishResults(keyword: CharSequence?, result: FilterResults?) {
                notifyDataSetChanged()
            }
        }
    }

    private fun sortByPositivityRate(list: MutableList<LookupData>): MutableList<LookupData> {
        return list.sortedByDescending {
                lookupData: LookupData -> lookupData.numOfPositiveCase
        } as MutableList<LookupData>
    }

}