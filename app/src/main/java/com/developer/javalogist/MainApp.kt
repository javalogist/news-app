package com.developer.javalogist

import android.app.Application
import com.developer.javalogist.data.Repository
import com.developer.javalogist.network.Api
import com.developer.javalogist.network.NewsManager

class MainApp : Application() {

    private val manager by lazy {
        NewsManager(Api.newsService)
    }

    val repository by lazy {
        Repository(manager = manager)
    }
}