package org.sopt.sample.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.sample.data.service.AuthService
import org.sopt.sample.data.service.FollowerService
import org.sopt.sample.data.service.MusicService
import org.sopt.sample.data.type.BaseUrlType
import org.sopt.sample.di.NetworkModule.Retrofit2
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Singleton
    @Provides
    fun provideAuthService(@Retrofit2(BaseUrlType.SOPT) retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Singleton
    @Provides
    fun provideMusicService(@Retrofit2(BaseUrlType.MUSIC) retrofit: Retrofit): MusicService =
        retrofit.create(MusicService::class.java)

    @Singleton
    @Provides
    fun provideFollowerService(@Retrofit2(BaseUrlType.REQRES) retrofit: Retrofit): FollowerService =
        retrofit.create(FollowerService::class.java)
}
