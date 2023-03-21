package com.developer.javalogist.network

import android.util.Log
import androidx.compose.runtime.*
import com.developer.javalogist.model.ArticleCategory
import com.developer.javalogist.model.TopNewsResponse
import com.developer.javalogist.model.getArticleCategory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsManager {
    private val _newsResponse = mutableStateOf(TopNewsResponse())
    val newsResponse: State<TopNewsResponse>
        @Composable get() = remember {
            _newsResponse
        }

    private val _getArticleByCategory = mutableStateOf(TopNewsResponse())
    val getArticleByCategory:MutableState<TopNewsResponse>
    @Composable get() = remember {
        _getArticleByCategory
    }

    val selectedCategory: MutableState<ArticleCategory?> = mutableStateOf(null)

    init {
        getArticles()
    }

    private fun getArticles() {
        val service = Api.newsService.getTopArticles("us")
        service.enqueue(object : Callback<TopNewsResponse> {
            override fun onResponse(
                call: Call<TopNewsResponse>,
                response: Response<TopNewsResponse>
            ) {
                if (response.isSuccessful && response.code() == 200) {
                    _newsResponse.value = response.body()!!
                    Log.d("news", "${_newsResponse.value}")
                } else {
                    Log.d("error", "${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("error", "${t.printStackTrace()}")
            }

        })
    }

    fun getArticlesByCategory(categoryName: String){
        val service = Api.newsService.getArticlesByCategory(categoryName)
        service.enqueue(object : Callback<TopNewsResponse> {
            override fun onResponse(
                call: Call<TopNewsResponse>,
                response: Response<TopNewsResponse>
            ) {
                if (response.isSuccessful && response.code() == 200) {
                    _getArticleByCategory.value = response.body()!!
                    Log.d("news", "${_getArticleByCategory.value}")
                } else {
                    Log.d("error", "${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("error", "${t.printStackTrace()}")
            }

        })
    }

    fun onSelectedCategoryChanged(categoryName: String) {
        val newCategory = getArticleCategory(categoryName)
        selectedCategory.value = newCategory
    }
}