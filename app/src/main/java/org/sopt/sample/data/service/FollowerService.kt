package org.sopt.sample.data.service

import org.sopt.sample.data.dto.response.ResponseFollower
import retrofit2.http.GET
import retrofit2.http.Query

interface FollowerService {
    @GET("/api/users?page=")
    suspend fun fetchFollowerList(
        @Query("page") site: Int = 2,
    ): ResponseFollower
}