package com.strangelove.cadastre.data.network.test

import com.strangelove.cadastre.data.network.CadastreApi

class TestRepositoryImpl(private val api: CadastreApi): TestRepository {
    override fun getCities() = api.getCity()
}