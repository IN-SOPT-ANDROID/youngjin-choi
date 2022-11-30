package org.sopt.sample.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseSignUp(
    val status: Int,
    val message: String,
    @SerialName("newUser")
    val result: ResponseUser,
)