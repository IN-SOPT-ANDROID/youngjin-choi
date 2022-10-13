package org.sopt.sample.presentation.github

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sopt.sample.databinding.ItemGithubHeaderBinding
import org.sopt.sample.databinding.ItemGithubRepositoryBinding
import org.sopt.sample.domain.RepositoryInfo
import org.sopt.sample.util.ItemDiffCallback

class GithubRepositoryListAdapter :
    ListAdapter<RepositoryInfo, RecyclerView.ViewHolder>(
        ItemDiffCallback<RepositoryInfo>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old == new }
        )) {

    private lateinit var inflater: LayoutInflater

    class HeaderViewHolder(private val binding: ItemGithubHeaderBinding) :
        RecyclerView.ViewHolder(binding.root)

    class ProfileViewHolder(private val binding: ItemGithubRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: RepositoryInfo) {
            binding.repository = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (!::inflater.isInitialized)
            inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            HEADER_VIEW_TYPE -> {
                HeaderViewHolder(ItemGithubHeaderBinding.inflate(inflater, parent, false))
            }
            else -> {
                ProfileViewHolder(ItemGithubRepositoryBinding.inflate(inflater, parent, false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProfileViewHolder -> holder.onBind(currentList[position - 1])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) HEADER_VIEW_TYPE else LIST_VIEW_TYPE
    }

    override fun getItemCount(): Int = currentList.size + 1

    companion object {
        private const val HEADER_VIEW_TYPE = 1
        private const val LIST_VIEW_TYPE = 2
    }
}