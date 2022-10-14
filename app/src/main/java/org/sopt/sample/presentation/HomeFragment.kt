package org.sopt.sample.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import org.sopt.sample.R
import org.sopt.sample.base.BindingFragment
import org.sopt.sample.databinding.FragmentHomeBinding
import org.sopt.sample.presentation.github.GithubRepositoryListAdapter
import org.sopt.sample.presentation.github.GithubViewModel
import org.sopt.sample.util.EventObserver
import timber.log.Timber
import java.io.IOException

class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel: GithubViewModel by viewModels()
    private val adapter = GithubRepositoryListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        initLayout()
        addObservers()
    }

    private fun initData() {
        getJsonStrFromAssets("fake_repo_list.json")?.let {
            viewModel.fetchRepositoryList(it)
        }
    }

    fun scrollToTop() {
        binding.rvRepositoryList.layoutManager?.scrollToPosition(0)
    }

    private fun initLayout() {
        binding.rvRepositoryList.adapter = adapter
    }

    private fun addObservers() {
        viewModel.repositoryInfo.observe(viewLifecycleOwner, EventObserver {
            adapter.submitList(it.toMutableList())
        })
    }

    private fun getJsonStrFromAssets(fileName: String): String? =
        try {
            val `is` = requireActivity().assets.open(fileName)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, charset("UTF-8"))
        } catch (e: IOException) {
            Timber.e(e.message)
            null
        }
}