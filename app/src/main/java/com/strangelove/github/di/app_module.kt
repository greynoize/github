package com.strangelove.github.di

import com.strangelove.github.ui.repositories.RepositoriesViewModel
import com.strangelove.github.data.network.test.GithubRepository
import com.strangelove.github.data.network.test.GithubRepositoryImpl
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext

val mainModule = applicationContext {
    bean { GithubRepositoryImpl(get()) as GithubRepository }

    viewModel { RepositoriesViewModel(get()) }
}

val githubApp = listOf(mainModule)