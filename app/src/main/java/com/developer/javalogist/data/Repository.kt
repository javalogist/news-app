package com.developer.javalogist.data

import com.developer.javalogist.network.NewsManager

class Repository(private val manager: NewsManager) {
    suspend fun getArticles() = manager.getArticles("us")
    suspend fun getArticlesByCategory(category: String) = manager.getArticlesByCategory(category)

}