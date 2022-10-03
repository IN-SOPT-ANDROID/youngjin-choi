package org.sopt.sample.application

import android.app.Application
import org.sopt.sample.BuildConfig
import org.sopt.sample.util.InSoptDebugTree
import timber.log.Timber

class InSoptApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(InSoptDebugTree())
        }
    }
}