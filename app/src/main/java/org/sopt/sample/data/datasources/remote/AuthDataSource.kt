package org.sopt.sample.data.datasources.remote

import org.sopt.sample.data.dto.request.RequestSignIn
import org.sopt.sample.data.dto.request.RequestSignUp
import org.sopt.sample.data.dto.response.ResponseSignIn
import org.sopt.sample.data.dto.response.ResponseSignUp
import org.sopt.sample.data.service.AuthService
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val authService: AuthService,
) {
    suspend fun signUp(signUpRequest: RequestSignUp): ResponseSignUp =
        authService.signUp(signUpRequest)

    suspend fun signIn(signInRequest: RequestSignIn): ResponseSignIn =
        authService.signIn(signInRequest)
}