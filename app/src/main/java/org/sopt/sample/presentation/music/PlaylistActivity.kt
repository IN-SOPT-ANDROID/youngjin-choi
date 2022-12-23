package org.sopt.sample.presentation.music

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.sample.R
import org.sopt.sample.base.BindingActivity
import org.sopt.sample.databinding.ActivityPlaylistBinding
import org.sopt.sample.domain.entity.Music
import org.sopt.sample.util.extensions.getParcelable

@AndroidEntryPoint
class PlaylistActivity : BindingActivity<ActivityPlaylistBinding>(R.layout.activity_playlist) {
    private val viewModel: MusicViewModel by viewModels()
    private val adapter = PlaylistAdapter()
    private lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.fetchMusicList()

        setMusicPostingResult()
        initLayout()
        addListener()
        addObservers()
    }

    private fun setMusicPostingResult() {
        launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode != Activity.RESULT_OK) return@registerForActivityResult
                val data = result.data ?: return@registerForActivityResult

                data.getParcelable(ARG_MUSIC_INFO, Music::class.java)?.let {
                    viewModel.addMusic(it)
                }
            }
    }

    private fun initLayout() {
        binding.rvPlaylist.adapter = adapter
    }

    private fun addListener() {
        binding.fabPosting.setOnClickListener {
            launcher.launch(Intent(this, MusicPostingActivity::class.java))
        }
        binding.layoutRefresh.setOnRefreshListener {
            viewModel.fetchMusicList()
            binding.layoutRefresh.isRefreshing = false
        }
    }

    private fun addObservers() {
        viewModel.musics.observe(this) { musics ->
            adapter.submitList(musics?.toMutableList())
        }
    }

    companion object {
        private const val ARG_MUSIC_INFO = "musicInfo"
    }
}
