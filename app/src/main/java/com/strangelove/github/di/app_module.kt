package com.strangelove.github.di

import com.strangelove.github.ui.repositories.repositories_list.RepositoriesViewModel
import com.strangelove.github.data.network.test.GithubRepository
import com.strangelove.github.data.network.test.GithubRepositoryImpl
import com.strangelove.github.ui.profile.ProfileViewModel
import com.strangelove.github.ui.repositories.repository_info.RepositoryViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext

val mainModule = applicationContext {
    bean { GithubRepositoryImpl(get()) as GithubRepository }

    viewModel { RepositoriesViewModel(get()) }
    viewModel { RepositoryViewModel() }
    viewModel { ProfileViewModel(get()) }
}

val githubApp = listOf(mainModule)