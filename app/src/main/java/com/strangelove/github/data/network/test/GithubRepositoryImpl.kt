package com.strangelove.github.data.network.test

import com.strangelove.github.data.network.GithubApi

class GithubRepositoryImpl(private val api: GithubApi): GithubRepository {
    override fun getRepositories() = api.getRepositories()
}