package org.sopt.sample.data.repositories

import org.sopt.sample.data.datasources.remote.FollowerDataSource
import org.sopt.sample.domain.entity.Follower
import org.sopt.sample.domain.repositories.FollowerRepository
import org.sopt.sample.util.extensions.times
import timber.log.Timber
import javax.inject.Inject

class FollowerRepositoryImpl @Inject constructor(
    private val followerDataSource: FollowerDataSource,
) : FollowerRepository {
    override suspend fun fetchFollowerList(): List<Follower>? = runCatching {
        followerDataSource.fetchFollowerList()
    }.fold({
        Timber.d("팔로워 목록 조회 성공")
        it.followers.map { follower -> follower.toFollower(follower) }.times(3)
    }, {
        Timber.e(it.message)
        null
    })
}
