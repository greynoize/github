package com.strangelove.github.data.network

import com.strangelove.github.data.model.repository.RepositoryInfo
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface GithubApi {
    @GET("repositories")
    fun getRepositories(): Single<Response<MutableList<RepositoryInfo?>>>
}