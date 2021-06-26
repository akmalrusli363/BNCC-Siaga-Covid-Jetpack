package com.tilikki.bnccapp.siagacovid.view.worldstats

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.tilikki.bnccapp.databinding.ItemCountryLookupBinding
import com.tilikki.bnccapp.siagacovid.model.CountryLookupData

class WorldStatLookupAdapter(private val countryLookupData: MutableList<CountryLookupData>) :
    RecyclerView.Adapter<WorldStatLookupViewHolder>(), Filterable {

    var filteredCountryLookupData: MutableList<CountryLookupData> = countryLookupData
    private var lookupDataComparator: Comparator<CountryLookupData> =
        WorldStatDataComparator.COMPARE_BY_POSITIVITY_RATE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorldStatLookupViewHolder {
        return WorldStatLookupViewHolder(
            ItemCountryLookupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holderLookup: WorldStatLookupViewHolder, position: Int) {
        holderLookup.bind(filteredCountryLookupData[position])
    }

    override fun getItemCount(): Int {
        return filteredCountryLookupData.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(keyword: CharSequence?): FilterResults {
                val query = keyword.toString()
                filteredCountryLookupData = if (query.isEmpty()) {
                    countryLookupData
                } else {
                    countryLookupData.filter {
                        it.countryName.contains(query, true)
                    } as MutableList<CountryLookupData>
                }
                return FilterResults().also {
                    filteredCountryLookupData = sortData(filteredCountryLookupData)
                    it.values = filteredCountryLookupData
                }
            }

            override fun publishResults(keyword: CharSequence?, result: FilterResults?) {
                notifyDataSetChanged()
            }
        }
    }

    fun setDataComparator(lookupDataComparator: Comparator<CountryLookupData>) {
        this.lookupDataComparator = lookupDataComparator
        filteredCountryLookupData = sortData(filteredCountryLookupData, lookupDataComparator)
        notifyDataSetChanged()
    }

    fun updateData(newList: List<CountryLookupData>) {
        countryLookupData.clear()
        countryLookupData.addAll(newList)
        notifyDataSetChanged()
        filteredCountryLookupData = sortData(countryLookupData)
    }

    private fun sortData(list: MutableList<CountryLookupData>): MutableList<CountryLookupData> {
        return sortData(list, lookupDataComparator)
    }

    private fun sortData(
        list: MutableList<CountryLookupData>?,
        comparator: Comparator<CountryLookupData>
    ): MutableList<CountryLookupData> {
        return list?.sortedWith(comparator) as MutableList<CountryLookupData>
    }

}
