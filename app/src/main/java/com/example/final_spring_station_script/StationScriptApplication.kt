package com.example.final_spring_station_script

import android.app.Application
import com.example.springprojectstationbuild.service.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import org.koin.core.context.GlobalContext


class StationScriptApplication : Application() {
    override fun onCreate(){
        super.onCreate()

        GlobalContext.startKoin{
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@StationScriptApplication)
            modules(appModule)
        }
    }
}