package sku.challenge.itunesmusicsearch.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import sku.challenge.itunesmusicsearch.R

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imgUrl: String) {
    Glide
        .with(view.context)
        .load(imgUrl)
        // .transition()
        .placeholder(R.drawable.no_thumbnail)
        .into(view)
}
