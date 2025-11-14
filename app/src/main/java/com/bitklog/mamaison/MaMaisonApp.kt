package com.bitklog.mamaison

import android.app.Application
import android.content.pm.ApplicationInfo
import com.bitklog.mamaison.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.ksp.generated.module

class MaMaisonApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val logLevel = if ((applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0)
            Level.ERROR
        else
            Level.NONE

        startKoin {
            androidLogger(logLevel)
            androidContext(this@MaMaisonApp)
            modules(
                AppModule().module
            )
        }
    }
}
