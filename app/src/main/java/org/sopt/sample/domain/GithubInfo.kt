package org.sopt.sample.domain

import java.util.*

data class GithubInfo(
    val nickname: String,
    val profileImg: String,
    val description: String,
    val id: String = UUID.randomUUID().toString(),
)