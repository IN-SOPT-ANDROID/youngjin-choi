package org.sopt.sample.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseSignIn(
    val status: Int,
    val message: String,
    val result: ResponseUser,
)