package org.sopt.sample

import android.os.Bundle
import androidx.activity.viewModels
import org.sopt.sample.base.BaseActivity
import org.sopt.sample.databinding.ActivitySignUpBinding

class SignUpActivity : BaseActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private val viewModel: SignViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}