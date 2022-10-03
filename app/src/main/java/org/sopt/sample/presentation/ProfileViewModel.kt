package org.sopt.sample.presentation

import androidx.lifecycle.ViewModel
import org.sopt.sample.presentation.model.UserInfo

class ProfileViewModel : ViewModel() {
    var user: UserInfo? = null

    fun setUserInfo(userInfo: UserInfo) {
        this.user = userInfo
    }
}