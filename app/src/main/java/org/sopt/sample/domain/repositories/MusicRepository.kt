package org.sopt.sample.domain.repositories

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.sopt.sample.data.dto.response.ResponseUploadMusic
import org.sopt.sample.domain.entity.Music

interface MusicRepository {
    suspend fun fetchMusicList(): Result<List<Music>?>
    suspend fun uploadMusic(
        musicRequest: RequestBody,
        image: MultipartBody.Part,
    ): Result<ResponseUploadMusic>
}