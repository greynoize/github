package com.strangelove.github.data.network

import com.strangelove.github.data.model.profile.Profile
import com.strangelove.github.data.model.repository.RepositoryInfo
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    @GET("repositories")
    fun getRepositories(@Query("since") since: Int): Single<Response<MutableList<RepositoryInfo?>>>

    @GET("users/mingorto")
    fun getProfile(): Single<Response<Profile>>
}