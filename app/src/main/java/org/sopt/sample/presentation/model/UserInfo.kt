package org.sopt.sample.presentation.model

import org.sopt.sample.presentation.types.Mbti
import java.io.Serializable

data class UserInfo(
    val id: String,
    val password: String,
    val name: String,
    val mbti: Mbti? = null,
) : Serializable
