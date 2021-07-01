package com.tilikki.siagacovid.view.hotline

import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tilikki.siagacovid.databinding.ItemHotlineBinding

class HotlineViewHolder(private val itemHotlineBinding: ItemHotlineBinding) :
    RecyclerView.ViewHolder(itemHotlineBinding.root) {

    fun bind(data: HotlineData) {
        itemHotlineBinding.run {
            tvHotlineContactName.text = data.name
            tvHotlinePhone.text = data.phone
            llHotline.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${data.phone}"))
                it.context.startActivity(intent)
            }
            Picasso.get().load(data.imgIcon).into(ivHotlineIcon)
        }
    }
}
