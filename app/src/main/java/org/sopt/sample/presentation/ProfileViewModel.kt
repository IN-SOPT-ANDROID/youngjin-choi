package org.sopt.sample.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.sopt.sample.presentation.model.UserInfo
import org.sopt.sample.util.InSoptSharedPreference
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(val inSoptSharedPreference: InSoptSharedPreference) :
    ViewModel() {
    var user: UserInfo? = null

    init {
        inSoptSharedPreference.getUserInfo()?.let {
            user = it
        }
    }

    // TODO delete
    fun setUserInfo(userInfo: UserInfo) {
        this.user = userInfo
    }
}