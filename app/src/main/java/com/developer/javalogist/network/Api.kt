package com.developer.javalogist.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Api {
    private const val BASE_URL = "https://newsapi.org/v2/"

    private val logging = HttpLoggingInterceptor()

    private val httpClient = OkHttpClient.Builder()
        .apply {
            addInterceptor(
                Interceptor() {
                    val builder = it.request().newBuilder()
                    builder.header("X-Api-Key", API_KEY)
                    return@Interceptor it.proceed(builder.build())
                }
            )
            logging.level = HttpLoggingInterceptor.Level.BODY
            addNetworkInterceptor(logging)
        }.build()

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(httpClient)
        .build()

    val newsService: NewsService by lazy {
        retrofit.create(NewsService::class.java)
    }
}