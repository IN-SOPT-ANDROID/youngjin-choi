package org.sopt.sample.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import org.sopt.sample.R
import org.sopt.sample.base.BaseActivity
import org.sopt.sample.databinding.ActivityHomeBinding
import org.sopt.sample.presentation.model.UserInfo

class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        intent.getBundleExtra(ARG_USER_BUNDLE)?.let {
            val userInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getSerializable(ARG_USER_INFO, UserInfo::class.java)
            } else {
                it.getSerializable(ARG_USER_INFO) as? UserInfo
            }

            userInfo?.let { viewModel.setUserInfo(it) }
        }
    }

    companion object {
        const val ARG_USER_BUNDLE = "userBundle"
        const val ARG_USER_INFO = "userInfo"
    }
}
