package org.sopt.sample.presentation.music

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sopt.sample.databinding.ItemMusicBinding
import org.sopt.sample.domain.entity.Music
import org.sopt.sample.util.ItemDiffCallback

class PlaylistAdapter :
    ListAdapter<Music, PlaylistAdapter.MusicViewHolder>(
        ItemDiffCallback<Music>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old.id == new.id }
        )) {

    private lateinit var inflater: LayoutInflater

    class MusicViewHolder(private val binding: ItemMusicBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Music) {
            binding.music = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        if (!::inflater.isInitialized)
            inflater = LayoutInflater.from(parent.context)
        return MusicViewHolder(ItemMusicBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }
}