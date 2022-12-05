package org.sopt.sample.data.datasources.remote

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.sopt.sample.data.dto.response.ResponseFetchMusic
import org.sopt.sample.data.dto.response.ResponseUploadMusic
import org.sopt.sample.data.service.MusicService
import javax.inject.Inject

class MusicDataSource @Inject constructor(
    private val musicService: MusicService,
) {
    suspend fun fetchMusicList(): ResponseFetchMusic =
        musicService.fetchMusicList()

    suspend fun uploadMusic(musicRequest: RequestBody, image: MultipartBody.Part): ResponseUploadMusic =
        musicService.uploadMusic(musicRequest, image)
}