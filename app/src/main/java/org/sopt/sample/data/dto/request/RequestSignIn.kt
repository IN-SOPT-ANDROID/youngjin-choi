package org.sopt.sample.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestSignIn(
    val email: String,
    val password: String,
)
