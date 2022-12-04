package org.sopt.sample.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseMusic(
    val id: Int,
    val image: String,
    val singer: String,
    val title: String
)