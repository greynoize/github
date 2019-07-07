package com.strangelove.cadastre.di

import com.strangelove.cadastre.ui.main.MainViewModel
import com.strangelove.cadastre.data.network.test.GithubRepository
import com.strangelove.cadastre.data.network.test.GithubRepositoryImpl
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext

val mainModule = applicationContext {
    bean { GithubRepositoryImpl(get()) as GithubRepository }

    viewModel { MainViewModel(get()) }
}

val cadastreApp = listOf(mainModule)