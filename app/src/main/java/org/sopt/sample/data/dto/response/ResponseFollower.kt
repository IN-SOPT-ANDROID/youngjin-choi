package org.sopt.sample.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sopt.sample.domain.entity.Follower

@Serializable
data class ResponseFollower(
    @SerialName("data")
    val followers: List<ResponseFollowerData>,
    val page: Int,
    @SerialName("per_page")
    val perPage: Int,
    val support: ResponseSupport,
    val total: Int,
    @SerialName("total_pages")
    val totalPages: Int,
) {
    @Serializable
    data class ResponseFollowerData(
        @SerialName("avatar")
        val profile: String,
        val email: String,
        @SerialName("first_name")
        val firstName: String,
        val id: Int,
        @SerialName("last_name")
        val lastName: String,
    ) {
        fun toFollower(follower: ResponseFollowerData): Follower =
            Follower(
                follower.id,
                follower.profile,
                follower.email,
                follower.firstName,
                follower.lastName
            )
    }

    @Serializable
    data class ResponseSupport(
        val text: String,
        val url: String,
    )
}