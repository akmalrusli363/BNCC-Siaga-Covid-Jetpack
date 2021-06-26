package com.tilikki.bnccapp.siagacovid.view.hotline

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tilikki.bnccapp.databinding.ItemHotlineBinding

class HotlineAdapter(private val hotlineList: MutableList<HotlineData>) :
    RecyclerView.Adapter<HotlineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotlineViewHolder {
        return HotlineViewHolder(
            ItemHotlineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: HotlineViewHolder, position: Int) {
        holder.bind(hotlineList[position])
    }

    override fun getItemCount(): Int {
        return hotlineList.size
    }

    fun updateData(newList: List<HotlineData>) {
        hotlineList.clear()
        hotlineList.addAll(newList)

        notifyDataSetChanged()
    }

}
