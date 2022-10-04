package org.sopt.sample.presentation

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.sample.R
import org.sopt.sample.base.BaseActivity
import org.sopt.sample.databinding.ActivityHomeBinding

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initLayout()

        /* TODO delete
            [필수과제] 로그인 화면에서 입력된 정보 전달 받기, 2주차 과제 시 해당 주석은 삭제될 예정 */
//        intent.getBundleExtra(ARG_USER_BUNDLE)?.let {
//            val userInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                it.getSerializable(ARG_USER_INFO, UserInfo::class.java)
//            } else {
//                it.getSerializable(ARG_USER_INFO) as? UserInfo
//            }
//
//            userInfo?.let { viewModel.setUserInfo(it) }
//        }
    }

    private fun initLayout() {
        binding.tvGithub.movementMethod = LinkMovementMethod.getInstance()
        binding.tvBlog.movementMethod = LinkMovementMethod.getInstance()
    }

    // TODO delete
    companion object {
        const val ARG_USER_BUNDLE = "userBundle"
        const val ARG_USER_INFO = "userInfo"
    }
}
