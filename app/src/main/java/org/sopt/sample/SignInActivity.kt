package org.sopt.sample

import android.content.Intent
import android.os.Bundle
import org.sopt.sample.base.BaseActivity
import org.sopt.sample.databinding.ActivitySignInBinding

class SignInActivity : BaseActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addListeners()
    }

    private fun addListeners() {
        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}