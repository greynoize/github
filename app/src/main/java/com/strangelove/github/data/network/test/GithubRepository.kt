package com.strangelove.github.data.network.test

import com.strangelove.github.data.model.profile.Profile
import com.strangelove.github.data.model.repository.RepositoryInfo
import io.reactivex.Single
import retrofit2.Response

interface GithubRepository {
    fun getRepositories(): Single<Response<MutableList<RepositoryInfo?>>>
    fun getProfile(): Single<Response<Profile>>
}