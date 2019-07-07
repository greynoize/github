package com.strangelove.cadastre.data.network.test

import com.strangelove.cadastre.data.network.GithubApi

class GithubRepositoryImpl(private val api: GithubApi): GithubRepository {
    override fun getCities() = api.getCity()
}