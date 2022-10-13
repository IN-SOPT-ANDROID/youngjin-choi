package org.sopt.sample.presentation

import android.os.Bundle
import android.view.View
import org.sopt.sample.R
import org.sopt.sample.base.BindingFragment
import org.sopt.sample.databinding.FragmentHomeBinding
import org.sopt.sample.domain.RepositoryInfo
import org.sopt.sample.presentation.github.GithubRepositoryListAdapter

class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val adapter = GithubRepositoryListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        initLayout()
    }

    private fun initData() {
        adapter.submitList(
            listOf(
                RepositoryInfo("Youngjinc",
                    "https://avatars.githubusercontent.com/u/48701368?v=4",
                    "android developer"),
                RepositoryInfo("Youngjinc",
                    "https://avatars.githubusercontent.com/u/48701368?v=4",
                    "android developer"),
                RepositoryInfo("Youngjinc",
                    "https://avatars.githubusercontent.com/u/48701368?v=4",
                    "android developer"),
                RepositoryInfo("Youngjinc",
                    "https://avatars.githubusercontent.com/u/48701368?v=4",
                    "android developer"),
                RepositoryInfo("Youngjinc",
                    "https://avatars.githubusercontent.com/u/48701368?v=4",
                    "android developer"),
                RepositoryInfo("Youngjinc",
                    "https://avatars.githubusercontent.com/u/48701368?v=4",
                    "android developer"),
                RepositoryInfo("Youngjinc",
                    "https://avatars.githubusercontent.com/u/48701368?v=4",
                    "android developer"),
                ).toMutableList()
        )
    }

    private fun initLayout() {
        binding.rvNameList.adapter = adapter
    }
}