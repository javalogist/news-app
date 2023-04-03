package com.developer.javalogist.ui

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.developer.javalogist.MainApp
import com.developer.javalogist.data.model.TopNewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = getApplication<MainApp>().repository

    private val _newsResponse = MutableStateFlow(TopNewsResponse())
    val newsResponse: StateFlow<TopNewsResponse> = _newsResponse


    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _getArticleByCategory = mutableStateOf(TopNewsResponse())
    val getArticleByCategory: MutableState<TopNewsResponse>
        @Composable get() = remember {
            _getArticleByCategory
        }

    fun getTopArticles() {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            _newsResponse.value = repository.getArticles()
        }
        _isLoading.value = false
    }
}