package com.strangelove.github.data.model.network

data class City(
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val mediumStoreConcentration: Boolean,
    val highStoreConcentration: Boolean
)