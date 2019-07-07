package com.strangelove.cadastre.data.network.test

import com.strangelove.cadastre.data.model.network.City
import io.reactivex.Single
import retrofit2.Response

interface GithubRepository {
    fun getCities(): Single<Response<MutableList<City>>>
}