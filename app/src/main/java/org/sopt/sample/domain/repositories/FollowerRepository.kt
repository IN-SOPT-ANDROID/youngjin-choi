package org.sopt.sample.domain.repositories

import org.sopt.sample.domain.entity.Follower

interface FollowerRepository {
    suspend fun fetchFollowerList(): List<Follower>?
}