package org.sopt.sample.presentation.sign

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.sample.R
import org.sopt.sample.base.BindingActivity
import org.sopt.sample.databinding.ActivitySignInBinding
import org.sopt.sample.presentation.HomeActivity
import org.sopt.sample.presentation.model.UserInfo
import org.sopt.sample.util.EventObserver
import org.sopt.sample.util.extensions.showToast

@AndroidEntryPoint
class SignInActivity : BindingActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {
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

                data.getBundleExtra(ARG_USER_INPUT)?.let {
                    val userInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        it.getParcelable(ARG_USER_INFO, UserInfo::class.java)
                    } else {
                        it.getParcelable(ARG_USER_INFO)
                    } ?: return@let
                    viewModel.setUserInfo(userInfo, it.getString(ARG_USER_PASSWORD) ?: return@let)
                }
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
        viewModel.isCompletedSignIn.observe(this, EventObserver { isCompleted ->
            showToast(getString(if (isCompleted) {
                moveToHome()
                R.string.sign_in_success_toast_message
            } else {
                R.string.sign_in_failure_toast_message
            }))
        })
    }

    private fun moveToHome() {
        /* TODO delete
          [필수과제] 로그인 화면에서 입력된 정보 전달 받기, 2주차 과제 시 해당 주석은 삭제될 예정 */

//        val intent = Intent(this, HomeActivity::class.java).apply {
//            Bundle().apply {
//                putParcelable(ARG_USER_INFO, viewModel.userInput)
//            }.also {
//                putExtra(ARG_USER_BUNDLE, it)
//            }
//        }
//
//        startActivity(intent)

        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    companion object {
        private const val ARG_USER_INPUT = "userInput"
        private const val ARG_USER_INFO = "userInfo"
        private const val ARG_USER_PASSWORD = "userPassword"
        const val ARG_USER_BUNDLE = "userBundle"
    }
}