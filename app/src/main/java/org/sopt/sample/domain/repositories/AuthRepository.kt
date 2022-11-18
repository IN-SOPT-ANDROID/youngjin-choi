package org.sopt.sample.domain.repositories

interface AuthRepository {
    suspend fun signUp(email: String, password: String, name: String): Boolean
    suspend fun signIn(email: String, password: String): Boolean
}