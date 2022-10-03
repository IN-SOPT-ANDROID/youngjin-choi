package org.sopt.sample.presentation.sign

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import org.sopt.sample.R
import org.sopt.sample.base.BaseActivity
import org.sopt.sample.databinding.ActivitySignUpBinding

class SignUpActivity : BaseActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private val viewModel: SignViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        addListeners()
    }

    private fun addListeners() {
        binding.back.setOnClickListener {
            finish()
        }

        binding.btnSignUp.setOnClickListener {
            moveToSignIn()
        }
    }

    private fun moveToSignIn() {
        val intent = Intent(this, SignInActivity::class.java)
        intent.putExtra(ARG_USER_ID, viewModel.id.value)
        intent.putExtra(ARG_USER_PASSWORD, viewModel.password.value)
        setResult(RESULT_OK, intent)
        finish()
    }

    companion object {
        const val ARG_USER_ID = "userId"
        const val ARG_USER_PASSWORD = "userPassword"
    }
}