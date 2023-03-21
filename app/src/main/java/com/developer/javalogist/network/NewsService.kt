package com.developer.javalogist.network

import com.developer.javalogist.model.TopNewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "717d3e541fa1492e8aa2e7f163972bf5"

interface NewsService {


    @GET("top-headlines")
    fun getTopArticles(
        @Query("country") country: String
    ): Call<TopNewsResponse>

    @GET("top-headlines")
    fun getArticlesByCategory(
        @Query("category") category: String
    ): Call<TopNewsResponse>

    @GET("everything")
    fun getArticlesBySource(
        @Query("sources") source: String
    ): Call<TopNewsResponse>


}