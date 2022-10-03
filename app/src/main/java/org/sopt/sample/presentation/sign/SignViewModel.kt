package org.sopt.sample.presentation.sign

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sopt.sample.presentation.types.Mbti
import org.sopt.sample.addSourceList
import org.sopt.sample.presentation.model.UserInfo

class SignViewModel : ViewModel() {
    val id = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val mbti = MutableLiveData<Mbti>()

    var userInput: UserInfo? = null

    private var _isCompletedSignIn = MutableLiveData<Boolean>()
    val isCompletedSignIn: LiveData<Boolean> get() = _isCompletedSignIn

    val isValidSignInput = MediatorLiveData<Boolean>().apply {
        addSourceList(id, password, name) { checkValidSignInput() }
    }

    private fun checkValidSignInput(): Boolean {
        if (name.value.isNullOrBlank() || id.value.isNullOrBlank() || password.value.isNullOrBlank()) return false
        return id.value!!.length in 6..10 && password.value!!.length in 8..12
    }

    fun signIn() {
        (id.value == userInput?.id && password.value == userInput?.password).let { isValid ->
            _isCompletedSignIn.value = isValid
        }
    }

    fun setUserInfo(userInput: UserInfo) {
        this.userInput = userInput
    }
}