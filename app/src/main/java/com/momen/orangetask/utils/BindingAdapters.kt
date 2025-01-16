package com.momen.orangetask.utils

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
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

@BindingAdapter("app:visibleIf")
fun ProgressBar.setVisibleIf(condition: Boolean) {
    visibility = if (condition) View.VISIBLE else View.GONE
}

@BindingAdapter("authorsText")
fun setAuthorsText(view: TextView, authors: List<String>?) {
    view.text = authors?.joinToString(", ") ?: "Unknown"
}