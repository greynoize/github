package com.strangelove.cadastre.data.network

import androidx.lifecycle.LiveData
import com.strangelove.cadastre.data.model.network.City
import retrofit2.http.GET

interface CadastreApi {
    @GET("cities")
    fun getCity(): LiveData<ApiResponse<MutableList<City>>>
}