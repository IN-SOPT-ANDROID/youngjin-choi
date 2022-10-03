package org.sopt.sample.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.sopt.sample.BuildConfig
import org.sopt.sample.util.InSoptDebugTree
import org.sopt.sample.util.InSoptSharedPreference
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class InSoptApplication : Application() {
    @Inject
    lateinit var inSoptSharedPreference: InSoptSharedPreference

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(InSoptDebugTree())
        }
    }
}