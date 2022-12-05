package org.sopt.sample.data.dto.response

import kotlinx.serialization.Serializable
import org.sopt.sample.domain.entity.Music

@Serializable
data class ResponseMusic(
    val id: Int,
    val image: String,
    val singer: String,
    val title: String
) {
    fun toMusic() = Music(
        id,
        image,
        singer,
        title
    )
}