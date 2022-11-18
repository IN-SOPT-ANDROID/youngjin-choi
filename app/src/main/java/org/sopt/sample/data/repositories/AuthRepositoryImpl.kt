package org.sopt.sample.data.repositories

import org.sopt.sample.data.datasources.remote.AuthDataSource
import org.sopt.sample.data.dto.request.RequestSignIn
import org.sopt.sample.data.dto.request.RequestSignUp
import org.sopt.sample.domain.repositories.AuthRepository
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
) : AuthRepository {
    override suspend fun signUp(email: String, password: String, name: String): Boolean =
        runCatching {
            authDataSource.signUp(RequestSignUp(email, password, name))
        }.fold({
            Timber.d(it.message)
            true
        }, {
            Timber.e(it.message)
            false
        })

    override suspend fun signIn(email: String, password: String): Boolean = runCatching {
        authDataSource.signIn(RequestSignIn(email, password))
    }.fold({
        Timber.d(it.message)
        true
    }, {
        Timber.e(it.message)
        false
    })
}
