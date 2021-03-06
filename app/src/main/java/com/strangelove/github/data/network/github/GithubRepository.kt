package com.strangelove.github.data.network.github

import com.strangelove.github.data.model.profile.Profile
import com.strangelove.github.data.model.repository.RepositoryInfo
import io.reactivex.Single
import retrofit2.Response

interface GithubRepository {
    fun getRepositories(since: Int): Single<Response<MutableList<RepositoryInfo?>>>
    fun getProfile(): Single<Response<Profile>>
}