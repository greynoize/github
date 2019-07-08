package com.strangelove.github.data.network.github

import com.strangelove.github.data.model.profile.Profile
import com.strangelove.github.data.network.GithubApi
import io.reactivex.Single
import retrofit2.Response

class GithubRepositoryImpl(private val api: GithubApi): GithubRepository {
    override fun getProfile(): Single<Response<Profile>> = api.getProfile()
    override fun getRepositories(since: Int) = api.getRepositories(since)
}