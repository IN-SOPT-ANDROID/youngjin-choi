package org.sopt.sample.domain

import java.util.UUID

data class GithubInfo(
    val id: String = UUID.randomUUID().toString(),
    val profileImg: String,
    val repoName: String,
    val nickname: String,
)