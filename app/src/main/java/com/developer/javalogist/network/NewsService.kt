package com.developer.javalogist.network

import com.developer.javalogist.data.model.TopNewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "717d3e541fa1492e8aa2e7f163972bf5"

interface NewsService {


    @GET("top-headlines")
   suspend fun getTopArticles(
        @Query("country") country: String
    ): TopNewsResponse

    @GET("top-headlines")
   suspend fun getArticlesByCategory(
        @Query("category") category: String
    ): TopNewsResponse

    @GET("everything")
   suspend fun getArticlesBySource(
        @Query("sources") source: String
    ): TopNewsResponse

    @GET("everything")
  suspend  fun getArticles(
        @Query("q") query: String
    ): TopNewsResponse


}