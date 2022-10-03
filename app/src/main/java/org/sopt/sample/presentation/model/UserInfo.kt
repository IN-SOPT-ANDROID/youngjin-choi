package org.sopt.sample.presentation.model

import org.sopt.sample.presentation.types.MbtiType
import org.sopt.sample.presentation.types.SoptPartType
import java.io.Serializable

data class UserInfo(
    val id: String,
    val password: String,
    val name: String,
    val mbti: MbtiType? = null,
    val age: Int = 24,
    val part: SoptPartType = SoptPartType.AOS,
    val university: String = "성신여대",
    val major: String = "컴퓨터공학과"
) : Serializable
