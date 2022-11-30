package org.sopt.sample.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseUser(
    val id: Int,
    val name: String,
    val profileImage: String?,
    val bio: String?,
    val email: String,
    val password: String,
)