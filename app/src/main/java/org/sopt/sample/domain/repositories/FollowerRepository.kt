package org.sopt.sample.domain.repositories

import org.sopt.sample.domain.entity.FollowerInfo

interface FollowerRepository {
    suspend fun fetchFollowerList(): List<FollowerInfo>?
}