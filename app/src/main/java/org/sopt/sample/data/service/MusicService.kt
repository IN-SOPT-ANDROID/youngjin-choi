package org.sopt.sample.data.service

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.sopt.sample.data.dto.response.ResponseFetchMusic
import org.sopt.sample.data.dto.response.ResponseUploadMusic
import retrofit2.http.*

interface MusicService {
    @GET("music/list")
    suspend fun fetchMusicList(): ResponseFetchMusic

    @Multipart
    @POST("music")
    suspend fun uploadMusic(
        @Part("request") request : RequestBody,
        @Part image: MultipartBody.Part,
    ): ResponseUploadMusic
}