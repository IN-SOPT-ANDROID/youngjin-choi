package org.sopt.sample.util

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import org.sopt.sample.BuildConfig
import org.sopt.sample.presentation.model.UserInfo
import org.sopt.sample.presentation.types.MbtiType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InSoptSharedPreference @Inject constructor(@ApplicationContext context: Context) {
    private val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val dataStore: SharedPreferences =
        if (BuildConfig.DEBUG) context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        else EncryptedSharedPreferences.create(
            context,
            FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    fun setUserInfo(user: UserInfo) {
        dataStore.edit().run {
            putString(PREF_USER_ID, user.id)
            putString(PREF_USER_PASSWORD, user.password)
            putString(PREF_USER_NAME, user.name)
            putString(PREF_USER_MBTI, user.mbti.toString())
        }.apply()
    }

    fun getUserInfo(): UserInfo? {
        with(dataStore) {
            val name = getString(PREF_USER_NAME, null)
            val id = getString(PREF_USER_ID, null)

            // 유저 이름이 존재하지 않는 경우, 미가입자로 판단
            if (name == null || id == null) return null

            return UserInfo(
                id,
                getString(PREF_USER_PASSWORD, null) ?: "",
                name,
                safeValueOf<MbtiType>(getString(PREF_USER_MBTI, null))
            )
        }
    }

    companion object {
        private const val FILE_NAME = "IN-SOPT"
        private const val PREF_USER_ID = "userId"
        private const val PREF_USER_PASSWORD = "userPassword"
        private const val PREF_USER_NAME = "userName"
        private const val PREF_USER_MBTI = "userMbti"
    }
}