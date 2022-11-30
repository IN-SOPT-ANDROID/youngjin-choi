package org.sopt.sample.presentation.sign

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.sample.R
import org.sopt.sample.base.BindingActivity
import org.sopt.sample.databinding.ActivitySignUpBinding
import org.sopt.sample.presentation.model.UserInfo
import org.sopt.sample.presentation.types.MbtiType
import org.sopt.sample.util.EventObserver
import org.sopt.sample.util.extensions.showToast
import org.sopt.sample.util.safeValueOf

@AndroidEntryPoint
class SignUpActivity : BindingActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private val viewModel: SignViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        addListeners()
        addObservers()
    }

    private fun addListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnSignUp.setOnClickListener {
            viewModel.signUp()
        }
    }

    private fun addObservers() {
        viewModel.isCompletedSignUp.observe(this, EventObserver { isCompleted ->
            showToast(getString(if (isCompleted) {
                moveToSignIn()
                R.string.sign_up_success_toast_message
            } else {
                R.string.sign_up_failure_toast_message
            }))
        })
    }

    private fun moveToSignIn() {
        val intent = Intent(this, SignInActivity::class.java)
        with(binding) {
            intent.putExtra(ARG_USER_INPUT, bundleOf(
                ARG_USER_INFO to UserInfo(email = etEmail.text.toString(),
                    id = etId.text.toString(),
                    mbti = safeValueOf<MbtiType>(etMbti.text.toString().uppercase())),
                ARG_USER_PASSWORD to etPassword.text.toString()
            ))
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    companion object {
        private const val ARG_USER_INPUT = "userInput"
        private const val ARG_USER_INFO = "userInfo"
        private const val ARG_USER_PASSWORD = "userPassword"
    }
}