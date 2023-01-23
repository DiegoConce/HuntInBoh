package com.example.huntinboh

import android.app.Application
import android.content.Context

class MainApplication : Application() {

    companion object {
        private lateinit var instance: MainApplication

        fun applicationContext(): Context? {
            if (this::instance.isInitialized)
                return instance
            return null
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}