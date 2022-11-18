package org.sopt.sample.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.sample.data.repositories.AuthRepositoryImpl
import org.sopt.sample.data.repositories.FollowerRepositoryImpl
import org.sopt.sample.domain.repositories.AuthRepository
import org.sopt.sample.domain.repositories.FollowerRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl,
    ): AuthRepository

    @Binds
    @Singleton
    fun bindFollowerRepository(
        followerRepositoryImpl: FollowerRepositoryImpl
    ): FollowerRepository
}
