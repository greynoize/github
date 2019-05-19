package com.strangelove.cadastre.di

import com.strangelove.cadastre.ui.main.MainViewModel
import com.strangelove.cadastre.data.network.test.TestRepository
import com.strangelove.cadastre.data.network.test.TestRepositoryImpl
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext

val mainModule = applicationContext {
    bean { TestRepositoryImpl(get()) as TestRepository }

    viewModel { MainViewModel(get()) }
}

val cadastreApp = listOf(mainModule)