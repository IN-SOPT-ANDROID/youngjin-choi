package org.sopt.sample.data.service

import org.sopt.sample.data.dto.request.RequestSignIn
import org.sopt.sample.data.dto.request.RequestSignUp
import org.sopt.sample.data.dto.response.ResponseSignIn
import org.sopt.sample.data.dto.response.ResponseSignUp
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("api/user/signin")
    suspend fun signIn(@Body request: RequestSignIn): ResponseSignIn

    @POST("api/user/signup")
    suspend fun signUp(@Body request: RequestSignUp): ResponseSignUp
}