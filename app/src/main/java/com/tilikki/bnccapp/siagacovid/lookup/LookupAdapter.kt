package com.tilikki.bnccapp.siagacovid.lookup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tilikki.bnccapp.R

class LookupAdapter(private val lookupList: MutableList<LookupData>) :
    RecyclerView.Adapter<LookupViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LookupViewHolder {
        return LookupViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_lookup, parent, false)
        )
    }

    override fun onBindViewHolder(holder: LookupViewHolder, position: Int) {
        holder.bind(lookupList[position])
    }

    override fun getItemCount(): Int {
        return lookupList.size
    }

}