package org.sopt.sample.presentation.sign

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import org.sopt.sample.R
import org.sopt.sample.base.BaseActivity
import org.sopt.sample.databinding.ActivitySignInBinding
import org.sopt.sample.presentation.HomeActivity
import org.sopt.sample.util.extensions.showSnackbar
import org.sopt.sample.util.extensions.showToast
import org.sopt.sample.util.safeLet

class SignInActivity : BaseActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private val viewModel: SignViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setSignUpResult()
        addListeners()
        addObservers()
    }

    private fun setSignUpResult() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode != Activity.RESULT_OK) return@registerForActivityResult
                val data = result.data ?: return@registerForActivityResult

                safeLet(data.getStringExtra(ARG_USER_ID),
                    data.getStringExtra(ARG_USER_PASSWORD)) { id, password ->
                    viewModel.setSignInfo(id, password)
                }

                binding.root.showSnackbar(getString(R.string.sign_up_success_snackbar_message))
            }
    }

    private fun addListeners() {
        binding.btnSignIn.setOnClickListener {
            viewModel.signIn()
        }

        binding.btnSignUp.setOnClickListener {
            resultLauncher.launch(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun addObservers() {
        viewModel.isCompletedSignIn.observe(this) { isCompleted ->
            if (isCompleted) {
                showToast(getString(R.string.sign_in_success_toast_message))
                moveToHome()
            } else {
                showToast(getString(R.string.sign_in_fail_toast_message))
            }
        }
    }

    private fun moveToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    companion object {
        const val ARG_USER_ID = "userId"
        const val ARG_USER_PASSWORD = "userPassword"
    }
}