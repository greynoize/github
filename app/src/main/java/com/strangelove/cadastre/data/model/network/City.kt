package com.strangelove.cadastre.data.model.network

data class City(
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val mediumStoreConcentration: Boolean,
    val highStoreConcentration: Boolean
)