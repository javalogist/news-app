package com.developer.javalogist.network

import androidx.compose.runtime.*
import com.developer.javalogist.data.model.ArticleCategory
import com.developer.javalogist.data.model.TopNewsResponse
import com.developer.javalogist.data.model.getArticleCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsManager(private val service: NewsService) {

    private val _newsResponse = mutableStateOf(TopNewsResponse())
    val newsResponse: State<TopNewsResponse>
        @Composable get() = remember {
            _newsResponse
        }



    val sourceName = mutableStateOf("abc-news")

    private val _getArticleBySource = mutableStateOf(TopNewsResponse())
    val getArticleBySource: MutableState<TopNewsResponse>
        @Composable get() = remember {
            _getArticleBySource
        }

    val selectedCategory: MutableState<ArticleCategory?> = mutableStateOf(null)

    val query = mutableStateOf("")

    private val _searchedNewsResponse = mutableStateOf(TopNewsResponse())
    val searchedNewsResponse: MutableState<TopNewsResponse>
        @Composable get() = remember {
            _searchedNewsResponse
        }


    suspend fun getArticles(country: String): TopNewsResponse = withContext(Dispatchers.IO) {
        service.getTopArticles(country)
    }

//    fun getArticlesByCategory(categoryName: String) {
//        val service = Api.newsService.getArticlesByCategory(categoryName)
//        service.enqueue(object : Callback<TopNewsResponse> {
//            override fun onResponse(
//                call: Call<TopNewsResponse>,
//                response: Response<TopNewsResponse>
//            ) {
//                if (response.isSuccessful && response.code() == 200) {
//                    _getArticleByCategory.value = response.body()!!
//                    Log.d("news", "${_getArticleByCategory.value}")
//                } else {
//                    Log.d("error", "${response.errorBody()}")
//                }
//            }
//
//            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
//                Log.d("error", "${t.printStackTrace()}")
//            }
//
//        })
//    }
//
//    fun getSearchedArticles(query: String) {
//        val service = Api.newsService.getArticlesByCategory(query)
//        service.enqueue(object : Callback<TopNewsResponse> {
//            override fun onResponse(
//                call: Call<TopNewsResponse>,
//                response: Response<TopNewsResponse>
//            ) {
//                if (response.isSuccessful && response.code() == 200) {
//                    _searchedNewsResponse.value = response.body()!!
//                    Log.d("news", "${_searchedNewsResponse.value}")
//                } else {
//                    Log.d("error", "${response.errorBody()}")
//                }
//            }
//
//            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
//                Log.d("error", "${t.printStackTrace()}")
//            }
//
//        })
//    }
//
//    fun getArticleBySource() {
//        val service = Api.newsService.getArticlesBySource(sourceName.value)
//        service.enqueue(object : Callback<TopNewsResponse> {
//            override fun onResponse(
//                call: Call<TopNewsResponse>,
//                response: Response<TopNewsResponse>
//            ) {
//                if (response.isSuccessful && response.code() == 200) {
//                    _getArticleBySource.value = response.body()!!
//                    Log.d("news", "${_getArticleBySource.value}")
//                } else {
//                    Log.d("error", "${response.errorBody()}")
//                }
//            }
//
//            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
//                Log.d("error", "${t.printStackTrace()}")
//            }
//
//        })
//    }

    fun onSelectedCategoryChanged(categoryName: String) {
        val newCategory = getArticleCategory(categoryName)
        selectedCategory.value = newCategory
    }
}