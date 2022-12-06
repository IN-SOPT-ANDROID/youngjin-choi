package org.sopt.sample.presentation.sign

import android.util.Patterns
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.sopt.sample.domain.repositories.AuthRepository
import org.sopt.sample.presentation.model.UserInfo
import org.sopt.sample.presentation.types.MbtiType
import org.sopt.sample.util.Event
import org.sopt.sample.util.InSoptSharedPreference
import org.sopt.sample.util.safeLet
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor(
    private val inSoptSharedPreference: InSoptSharedPreference,
    private val authRepository: AuthRepository,
) :
    ViewModel() {
    val id = MutableLiveData<String?>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val mbti = MutableLiveData<MbtiType?>()

    private val _isCompletedSignIn = MutableLiveData<Event<Boolean>>()
    val isCompletedSignIn: LiveData<Event<Boolean>> get() = _isCompletedSignIn

    private val _isCompletedSignUp = MutableLiveData<Event<Boolean>>()
    val isCompletedSignUp: LiveData<Event<Boolean>> get() = _isCompletedSignUp

    val isValidId: LiveData<Boolean>
        get() = Transformations.map(id) { id -> id?.matches(ID_PATTERN.toRegex()) }

    val isValidEmail: LiveData<Boolean>
        get() = Transformations.map(email) { email -> email?.matches(Patterns.EMAIL_ADDRESS.toRegex()) }

    val isValidPassword: LiveData<Boolean>
        get() = Transformations.map(password) { pw -> pw?.matches(PASSWORD_PATTERN.toRegex()) }

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
            safeLet(email.value, password.value) { email, password ->
                val isSuccessful = authRepository.signIn(email, password)
                if (isSuccessful) inSoptSharedPreference.setUserInfo(id.value, email, mbti.value)
                _isCompletedSignIn.value = Event(isSuccessful)
            }
        }
    }

    fun setUserInfo(userInput: UserInfo, password: String) {
        id.value = userInput.id
        email.value = userInput.email
        mbti.value = userInput.mbti
        this.password.value = password
    }

    companion object {
        private const val ID_PATTERN = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{6,10}\$"
        private const val PASSWORD_PATTERN =
            "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*#?&.])[A-Za-z[0-9]\$@\$!%*#?&.]{6,12}\$"
    }
}