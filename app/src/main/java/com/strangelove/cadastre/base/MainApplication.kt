package com.strangelove.cadastre.base

import android.app.Application
import com.strangelove.cadastre.di.cadastreApp
import com.strangelove.cadastre.di.dataModule
import org.koin.android.ext.android.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, cadastreApp + dataModule)
    }
}