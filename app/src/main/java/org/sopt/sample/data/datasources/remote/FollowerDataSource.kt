package org.sopt.sample.data.datasources.remote

import org.sopt.sample.data.dto.response.ResponseFollower
import org.sopt.sample.data.service.FollowerService
import javax.inject.Inject

class FollowerDataSource @Inject constructor(
    private val followerService: FollowerService,
) {
    suspend fun fetchFollowerList(): ResponseFollower =
        followerService.fetchFollowerList()
}