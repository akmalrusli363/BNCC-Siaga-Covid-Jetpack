package com.tilikki.bnccapp.siagacovid.hotline

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_hotline.view.*

class HotlineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(data: HotlineData) {
        itemView.llHotline.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${data.phone}"))
            it.context.startActivity(intent)
        }

        itemView.tvHotlineContactName.text = data.name
        itemView.tvHotlinePhone.text = data.phone
        Picasso.get().load(data.imgIcon).into(itemView.ivHotlineIcon)
    }
}