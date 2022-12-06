package org.sopt.sample.util

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.load
import org.sopt.sample.R

@BindingAdapter("app:image")
fun ImageView.setImage(imageUrl: String) {
    this.load(imageUrl) {
        placeholder(R.color.gray_150)
    }
}

@BindingAdapter("visibility")
fun View.setVisibility(isVisible: Boolean?) {
    if (isVisible == null) return
    this.isVisible = isVisible
}