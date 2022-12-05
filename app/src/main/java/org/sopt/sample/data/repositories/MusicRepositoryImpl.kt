package org.sopt.sample.data.repositories

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.sopt.sample.data.datasources.remote.AuthDataSource
import org.sopt.sample.data.datasources.remote.MusicDataSource
import org.sopt.sample.data.dto.request.RequestSignIn
import org.sopt.sample.data.dto.request.RequestSignUp
import org.sopt.sample.domain.repositories.AuthRepository
import org.sopt.sample.domain.repositories.MusicRepository
import timber.log.Timber
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val musicDataSource: MusicDataSource
) : MusicRepository {
    override suspend fun fetchMusicList() = runCatching {
            musicDataSource.fetchMusicList()
        }.map { it.data?.map { it.toMusic() } }

    override suspend fun uploadMusic(musicRequest: RequestBody, image: MultipartBody.Part) = runCatching {
        musicDataSource.uploadMusic(musicRequest, image)
    }
}
