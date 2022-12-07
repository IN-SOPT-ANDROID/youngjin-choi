package org.sopt.sample.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Music(
    val id: Int,
    val image: String,
    val singer: String,
    val title: String
) : Parcelable