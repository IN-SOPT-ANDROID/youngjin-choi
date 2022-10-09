package org.sopt.sample.presentation.sign

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.sopt.sample.presentation.model.UserInfo
import org.sopt.sample.util.Event
import org.sopt.sample.util.InSoptSharedPreference
import org.sopt.sample.util.extensions.addSourceList
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor(private val inSoptSharedPreference: InSoptSharedPreference) :
    ViewModel() {
    val id = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val name = MutableLiveData<String>()

    var userInput: UserInfo? = null

    private var _isCompletedSignIn = MutableLiveData<Event<Boolean>>()
    val isCompletedSignIn: LiveData<Event<Boolean>> get() = _isCompletedSignIn

    val isValidSignInput = MediatorLiveData<Boolean>().apply {
        addSourceList(id, password, name) { checkValidSignInput() }
    }

    private fun checkValidSignInput(): Boolean {
        if (name.value.isNullOrBlank() || id.value.isNullOrBlank() || password.value.isNullOrBlank()) return false
        return id.value!!.length in 6..10 && password.value!!.length in 8..12
    }

    fun signIn() {
        (id.value == userInput?.id && password.value == userInput?.password).let { isValid ->
            if (isValid && userInput != null) inSoptSharedPreference.setUserInfo(userInput!!)
            _isCompletedSignIn.value = Event(isValid)
        }
    }

    fun setUserInfo(userInput: UserInfo) {
        this.userInput = userInput
    }
}