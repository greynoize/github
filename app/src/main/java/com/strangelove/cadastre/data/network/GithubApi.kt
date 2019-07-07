package com.strangelove.cadastre.data.network

import com.strangelove.cadastre.data.model.network.City
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface GithubApi {
    @GET("cities")
    fun getCity(): Single<Response<MutableList<City>>>
}