package org.sopt.sample.presentation.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.sopt.sample.R
import org.sopt.sample.base.BaseActivity
import org.sopt.sample.databinding.ActivitySplashBinding
import org.sopt.sample.presentation.HomeActivity
import org.sopt.sample.presentation.sign.SignInActivity
import org.sopt.sample.util.EventObserver

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addListeners()
    }

    private fun addListeners() {
        viewModel.isSignedUser.observe(this, EventObserver { isSigned ->
            lifecycleScope.launch {
                delay(2000)
                moveToNext(isSigned)
                finish()
            }
        })
    }

    private fun moveToNext(isSigned: Boolean) {
        startActivity(Intent(this@SplashActivity, if (isSigned) {
            HomeActivity::class.java
        } else {
            SignInActivity::class.java
        }))
    }
}
