package com.tilikki.bnccapp.siagacovid.hotline

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_hotline.view.*

class HotlineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(data: HotlineData) {
        itemView.tvLookUpProvince.text = data.name
        itemView.tvHotlinePhone.text = data.phone
        Picasso.get().load(data.imgIcon).into(itemView.ivHotlineIcon)
    }
}