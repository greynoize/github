package com.strangelove.github.base

import android.app.Application
import com.strangelove.github.di.githubApp
import com.strangelove.github.di.dataModule
import org.koin.android.ext.android.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, githubApp + dataModule)
    }
}