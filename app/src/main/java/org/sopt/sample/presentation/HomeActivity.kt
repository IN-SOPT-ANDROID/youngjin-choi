package org.sopt.sample.presentation

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.sample.R
import org.sopt.sample.base.BindingActivity
import org.sopt.sample.databinding.ActivityHomeBinding

@AndroidEntryPoint
class HomeActivity : BindingActivity<ActivityHomeBinding>(R.layout.activity_home) {
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initLayout()
    }

    private fun initLayout() {
        binding.tvGithub.movementMethod = LinkMovementMethod.getInstance()
        binding.tvBlog.movementMethod = LinkMovementMethod.getInstance()
    }
}
