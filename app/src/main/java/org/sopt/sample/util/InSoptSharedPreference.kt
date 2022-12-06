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

    fun setUserInfo(id: String?, email: String, mbti: MbtiType?) {
        dataStore.edit().run {
            mbti?.let { putString(PREF_USER_MBTI, it.name) }
            putString(PREF_USER_EMAIL, email)
            putString(PREF_USER_ID, id)
        }.apply()
    }

    fun getUserInfo(): UserInfo? {
        with(dataStore) {
            val id = getString(PREF_USER_ID, null)
            val email = getString(PREF_USER_EMAIL, null) ?: return null // 유저 이메일이 존재하지 않는 경우, 미가입자로 판단
            val mbti = safeValueOf<MbtiType>(getString(PREF_USER_MBTI, null))

            return if (id == null)
                UserInfo(
                    email = email,
                    mbti = mbti
                )
            else
                UserInfo(
                    id = id,
                    email = email,
                    mbti = mbti
                )
        }
    }

    companion object {
        private const val FILE_NAME = "IN-SOPT"
        private const val PREF_USER_EMAIL = "userEmail"
        private const val PREF_USER_ID = "userId"
        private const val PREF_USER_MBTI = "userMbti"
    }
}