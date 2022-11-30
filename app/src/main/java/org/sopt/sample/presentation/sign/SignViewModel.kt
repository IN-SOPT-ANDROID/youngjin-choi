package org.sopt.sample.presentation.sign

import android.util.Patterns
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.sopt.sample.domain.repositories.AuthRepository
import org.sopt.sample.presentation.model.UserInfo
import org.sopt.sample.util.Event
import org.sopt.sample.util.InSoptSharedPreference
import org.sopt.sample.util.extensions.addSourceList
import org.sopt.sample.util.safeLet
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor(
    private val inSoptSharedPreference: InSoptSharedPreference,
    private val authRepository: AuthRepository,
) :
    ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val id = MutableLiveData<String>()

    var userInput: UserInfo? = null

    private val _isCompletedSignIn = MutableLiveData<Event<Boolean>>()
    val isCompletedSignIn: LiveData<Event<Boolean>> get() = _isCompletedSignIn

    private val _isCompletedSignUp = MutableLiveData<Event<Boolean>>()
    val isCompletedSignUp: LiveData<Event<Boolean>> get() = _isCompletedSignUp

    val isValidSignInput = MediatorLiveData<Boolean>().apply {
        addSourceList(email, password, id) { checkValidSignInput() }
    }

    private fun checkValidSignInput(): Boolean {
        if (id.value.isNullOrBlank() || email.value.isNullOrBlank() || password.value.isNullOrBlank()) return false
        return Patterns.EMAIL_ADDRESS.matcher(email.value!!)
            .matches() && password.value!!.length in 8..12
    }

    fun signUp() {
        viewModelScope.launch {
            safeLet(email.value, password.value, id.value) { id, password, name ->
                val isSuccessful = authRepository.signUp(id, password, name)
                _isCompletedSignUp.value = Event(isSuccessful)
            }
        }
    }

    fun signIn() {
        viewModelScope.launch {
            safeLet(email.value, password.value) { id, password ->
                val isSuccessful = authRepository.signIn(id, password)
                if (isSuccessful) inSoptSharedPreference.setUserInfo(userInput!!)
                _isCompletedSignIn.value = Event(isSuccessful)
            }
        }
    }

    fun setUserInfo(userInput: UserInfo) {
        this.userInput = userInput
    }
}