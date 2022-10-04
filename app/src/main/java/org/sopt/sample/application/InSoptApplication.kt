package org.sopt.sample.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.sopt.sample.BuildConfig
import org.sopt.sample.util.InSoptDebugTree
import timber.log.Timber

@HiltAndroidApp
class InSoptApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(InSoptDebugTree())
        }
    }
}