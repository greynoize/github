package com.strangelove.cadastre.data.network.test

import androidx.lifecycle.LiveData
import com.strangelove.cadastre.data.model.network.City
import com.strangelove.cadastre.data.network.ApiResponse

interface TestRepository {
    fun getCities(): LiveData<ApiResponse<MutableList<City>>>
}