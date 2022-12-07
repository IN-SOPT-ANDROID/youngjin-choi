package org.sopt.sample.presentation.music

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.sample.R
import org.sopt.sample.base.BindingActivity
import org.sopt.sample.databinding.ActivityMusicPostingBinding
import org.sopt.sample.util.extensions.showToast

@AndroidEntryPoint
class MusicPostingActivity :
    BindingActivity<ActivityMusicPostingBinding>(R.layout.activity_music_posting) {
    private val viewModel: MusicViewModel by viewModels()

    private val storagePermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                galleryLauncher.launch("image/*")
            }
        }

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        viewModel.selectedGalleryImageUri.value = it
        binding.ivAlbumCover.load(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        addListener()
        addObservers()
    }

    private fun addListener() {
        binding.ivCamera.setOnClickListener {
            storagePermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        binding.ivKeyboard.setOnClickListener {
            // TODO 키보드 숨기기
        }
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun addObservers() {
        viewModel.music.observe(this) { music ->
            if (music == null) return@observe
            val intent = Intent(this, PlaylistActivity::class.java).putExtra(ARG_MUSIC_INFO, music)
            setResult(RESULT_OK, intent)
            finish()
        }

        viewModel.isCompletedUpload.observe(this) { isCompleted ->
            showToast(getString(if (isCompleted) R.string.music_posting_success_toast_message else R.string.music_posting_failure_toast_message))
        }
    }

    companion object {
        private const val ARG_MUSIC_INFO = "musicInfo"
    }
}
