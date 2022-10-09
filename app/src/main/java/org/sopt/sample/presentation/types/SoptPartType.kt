package org.sopt.sample.presentation.types

import androidx.annotation.StringRes
import org.sopt.sample.R

enum class SoptPartType(@StringRes val strRes: Int) {
    AOS(R.string.part_aos),
    IOS(R.string.part_ios),
    WEB(R.string.part_web),
    SERVER(R.string.part_server),
    DESIGN(R.string.part_design),
    PLAN(R.string.part_plan)
}