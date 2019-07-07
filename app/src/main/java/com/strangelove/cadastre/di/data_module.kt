package com.strangelove.cadastre.di

import com.google.gson.Gson
import com.strangelove.cadastre.data.network.GithubApi
import com.strangelove.cadastre.di.DatasourceProperties.SERVER_URL
import com.strangelove.cadastre.di.DatasourceProperties.TIMEOUT
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = applicationContext {
    bean { createOkHttpClient() }
    bean { createWebService(get()) }
    bean { Gson() }
}

object DatasourceProperties {
    const val SERVER_URL = "https://lenta.com/api/v1/"
    const val TIMEOUT = 60L
}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder()
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

fun createWebService(okHttpClient: OkHttpClient): GithubApi {
    val retrofit = Retrofit.Builder()
        .baseUrl(SERVER_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
    return retrofit.create(GithubApi::class.java)
}