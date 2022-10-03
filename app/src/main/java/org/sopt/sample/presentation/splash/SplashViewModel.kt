package org.sopt.sample.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.sopt.sample.util.InSoptSharedPreference
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val inSoptSharedPreference: InSoptSharedPreference,
) : ViewModel() {
    private val _isSignedUser = MutableLiveData<Boolean>()
    val isSignedUser: LiveData<Boolean> get() = _isSignedUser

    init {
        checkSignedUser()
    }

    private fun checkSignedUser() {
        viewModelScope.launch {
            _isSignedUser.value = inSoptSharedPreference.getUserInfo() != null
        }
    }

    companion object {
        private const val TAG = "SplashViewModel"
    }
}