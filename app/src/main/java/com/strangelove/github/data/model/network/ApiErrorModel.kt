package com.strangelove.github.data.model.network

data class ApiErrorModel(
    val error: String? = null,
    val errorDescription: String? = null,
    var errorCode: String = "",
    val message: String = "",
    val updateMessage: String? = null,
    val updateUrl: String? = null
)