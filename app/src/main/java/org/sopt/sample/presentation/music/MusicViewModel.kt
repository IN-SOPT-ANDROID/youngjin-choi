package org.sopt.sample.presentation.music

import android.app.Application
import android.net.Uri
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.sopt.sample.domain.entity.Music
import org.sopt.sample.domain.repositories.MusicRepository
import org.sopt.sample.util.ContentUriRequestBody
import org.sopt.sample.util.InSoptSharedPreference
import org.sopt.sample.util.extensions.addSourceList
import org.sopt.sample.util.extensions.toJsonRequestBody
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MusicViewModel @Inject constructor(
    private val application: Application, // TODO 리팩토링 필요
    private val musicRepository: MusicRepository,
    private val inSoptSharedPreference: InSoptSharedPreference,
) :
    ViewModel() {
    private val _musics = MutableLiveData<MutableList<Music>?>(mutableListOf())
    val musics: LiveData<MutableList<Music>?> get() = _musics
    private val _music = MutableLiveData<Music>()
    val music: LiveData<Music> get() = _music
    val selectedGalleryImageUri = MutableLiveData<Uri?>()
    val singer = MutableLiveData<String>()
    val title = MutableLiveData<String>()
    private val _isCompletedUpload = MutableLiveData<Boolean>()
    val isCompletedUpload: LiveData<Boolean> get() = _isCompletedUpload
    val nickname: String? get() = inSoptSharedPreference.getUserInfo()?.name ?: "영진"
    val isEnabledCompleteButton = MediatorLiveData<Boolean>().apply {
        addSourceList(singer,
            title,
            selectedGalleryImageUri) { !(singer.value.isNullOrBlank() || title.value.isNullOrBlank() || selectedGalleryImageUri.value == null) }
    }

    fun fetchMusicList() {
        viewModelScope.launch {
            musicRepository.fetchMusicList()
                .onSuccess {
                    _musics.value = it?.reversed()?.toMutableList()
                }.onFailure {
                    Timber.d(it.message)
                }
        }
    }

    fun uploadMusic() {
        viewModelScope.launch {
            val imageMultipartBody = ContentUriRequestBody(
                application.baseContext,
                "image",
                selectedGalleryImageUri.value!!
            ).toFormData()

            val jsonRequestBody =
                JSONObject("{\"singer\":\"${singer.value}\",\"title\":\"${title.value}\"}").toString()
                    .toJsonRequestBody()

            musicRepository.uploadMusic(jsonRequestBody, imageMultipartBody)
                .onSuccess {
                    _music.value = it.data?.toMusic()
                    _isCompletedUpload.value = true
                }.onFailure {
                    _isCompletedUpload.value = false
                    Timber.e(it.message)
                }
        }
    }

    fun addMusic(music: Music) {
        _musics.value = _musics.value?.apply {
            add(0, music)
        }
    }
}