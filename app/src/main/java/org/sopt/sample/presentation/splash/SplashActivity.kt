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

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    private val viewModel: SplashViewModel by viewModels()
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addListeners()
    }

    private fun addListeners() {
        viewModel.isSignedUser.observe(this) { isSigned ->
            lifecycleScope.launch {
                job = launch {
                    delay(2000)
                    moveToNext(isSigned)
                    finish()
                }
            }
        }
    }

    private fun moveToNext(isSigned: Boolean) {
        lifecycleScope.launch {
            if (isSigned) startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
            else startActivity(Intent(this@SplashActivity, SignInActivity::class.java))
        }
    }

    override fun onPause() {
        job?.cancel()
        super.onPause()
    }
}
