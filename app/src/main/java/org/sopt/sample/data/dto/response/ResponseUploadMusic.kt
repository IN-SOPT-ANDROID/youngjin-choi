package org.sopt.sample.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseUploadMusic(
    val statusCode: Int,
    val success: Boolean,
    val message: String,
    val data: List<ResponseMusic>?,
)