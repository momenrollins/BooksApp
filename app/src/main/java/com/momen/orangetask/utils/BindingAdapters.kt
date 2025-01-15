package com.momen.orangetask.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil3.load
import coil3.request.crossfade
import coil3.request.placeholder
import com.momen.orangetask.R

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    view.load(url) {
        crossfade(true)
        placeholder(R.mipmap.ic_launcher_foreground)
    }
}

@BindingAdapter("authorsText")
fun setAuthorsText(view: TextView, authors: List<String>?) {
    view.text = authors?.joinToString(", ") ?: "Unknown"
}