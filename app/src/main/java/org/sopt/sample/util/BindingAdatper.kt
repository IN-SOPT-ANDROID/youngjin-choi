package org.sopt.sample.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import org.sopt.sample.R

@BindingAdapter("app:image")
fun ImageView.setImage(imageUrl: String) {
    this.load(imageUrl) {
        placeholder(R.color.gray_150)
    }
}