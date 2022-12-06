package org.sopt.sample.presentation.music

import android.app.Application
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.sopt.sample.domain.entity.Music
import org.sopt.sample.domain.repositories.MusicRepository
import org.sopt.sample.util.ContentUriRequestBody
import org.sopt.sample.util.extensions.toJsonRequestBody
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MusicViewModel @Inject constructor(
    private val application: Application, // TODO 리팩토링 필요
    private val musicRepository: MusicRepository,
) :
    ViewModel() {
    private val _musics = MutableLiveData<List<Music>?>()
    val musics: LiveData<List<Music>?> get() = _musics
    private val selectedGalleryImageUri = MutableLiveData<Uri?>()
    val singer = MutableLiveData<String>()
    val title = MutableLiveData<String>()
    private val _isCompletedUpload = MutableLiveData<Boolean>()
    val isCompletedUpload: LiveData<Boolean> get() = _isCompletedUpload

    init {
        fetchMusicList()
    }

    private fun fetchMusicList() {
        viewModelScope.launch {
            musicRepository.fetchMusicList()
                .onSuccess {
                    _musics.value = it
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
                JSONObject("{\"singer\":\"${singer.value}\",\"title\":\"${title.value}\"").toString()
                    .toJsonRequestBody()

            musicRepository.uploadMusic(jsonRequestBody, imageMultipartBody)
                .onSuccess {
                    _isCompletedUpload.value = true
                }.onFailure {
                    _isCompletedUpload.value = false
                    Timber.e(it.message)
                }
        }
    }
}