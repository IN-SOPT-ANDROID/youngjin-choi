package org.sopt.sample.presentation.follower

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sopt.sample.databinding.ItemFollowerBinding
import org.sopt.sample.domain.entity.FollowerInfo
import org.sopt.sample.util.ItemDiffCallback

class FollowerListAdapter :
    ListAdapter<FollowerInfo, FollowerListAdapter.FollowerViewHolder>(
        ItemDiffCallback<FollowerInfo>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old == new }
        )) {

    private lateinit var inflater: LayoutInflater

    class FollowerViewHolder(private val binding: ItemFollowerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: FollowerInfo) {
            binding.follower = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        if (!::inflater.isInitialized)
            inflater = LayoutInflater.from(parent.context)

        return FollowerViewHolder(ItemFollowerBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

}