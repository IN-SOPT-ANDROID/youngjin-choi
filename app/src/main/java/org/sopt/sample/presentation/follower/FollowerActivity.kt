package org.sopt.sample.presentation.follower

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.sample.R
import org.sopt.sample.base.BindingActivity
import org.sopt.sample.databinding.ActivityFlowerBinding

@AndroidEntryPoint
class FollowerActivity : BindingActivity<ActivityFlowerBinding>(R.layout.activity_flower) {
    private val viewModel: FollowerViewModel by viewModels()
    private val adapter = FollowerListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initLayout()
        addObservers()
    }

    private fun initLayout() {
        binding.rvFollowerList.adapter = adapter
    }

    private fun addObservers() {
        viewModel.followers.observe(this) {
            adapter.submitList(it?.toMutableList())
        }
    }
}