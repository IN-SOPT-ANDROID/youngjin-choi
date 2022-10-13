package org.sopt.sample.domain

import java.util.*

data class RepositoryInfo(
    val name: String,
    val image: String,
    val description: String,
    val id: String = UUID.randomUUID().toString(),
)