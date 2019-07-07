package com.strangelove.github.data.network

import com.strangelove.github.data.model.network.City
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface GithubApi {
    @GET("cities")
    fun getCity(): Single<Response<MutableList<City>>>
}