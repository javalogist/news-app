package com.developer.javalogist.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.developer.javalogist.components.BottomMenu
import com.developer.javalogist.data.model.TopNewsArticle
import com.developer.javalogist.network.Api
import com.developer.javalogist.network.NewsManager
import com.developer.javalogist.ui.screen.Categories
import com.developer.javalogist.ui.screen.DetailScreen
import com.developer.javalogist.ui.screen.Sources
import com.developer.javalogist.ui.screen.TopNews

@Composable
fun NewsApp(mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    val scrollState = rememberScrollState()
    MainScreen(navController = navController, scrollState = scrollState, mainViewModel)
}

@Composable
fun MainScreen(
    navController: NavHostController,
    scrollState: ScrollState,
    mainViewModel: MainViewModel
) {
    Scaffold(
        bottomBar = { BottomMenu(navController = navController) }
    ) {
        Navigation(navController, scrollState, it, mainViewModel)
    }
}

@Composable
fun Navigation(
    navController: NavHostController,
    scrollState: ScrollState,
    paddingValues: PaddingValues,
    mainViewModel: MainViewModel,
    newsManager: NewsManager = NewsManager(Api.newsService)
) {

    val articles = mutableListOf<TopNewsArticle>()
    articles.addAll(newsManager.newsResponse.value.articles ?: listOf(TopNewsArticle()))
    NavHost(
        navController = navController,
        startDestination = BottomMenuScreen.TopNews.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        bottomNavigation(navController = navController, articles, newsManager)
        composable(
            "DetailScreen/{index}",
            arguments = listOf(navArgument("index") { type = NavType.IntType })
        ) {
            val index = it.arguments?.getInt("index")
            index?.let { ind ->
                if (newsManager.query.value.isNotEmpty()) {
                    articles.clear()
                    articles.addAll(newsManager.searchedNewsResponse.value.articles ?: listOf())
                } else {
                    articles.clear()
                    articles.addAll(newsManager.newsResponse.value.articles ?: listOf())
                }
                val article = articles[ind]
                DetailScreen(navController, article, scrollState)
            }

        }
    }
}


fun NavGraphBuilder.bottomNavigation(
    navController: NavHostController,
    articles: List<TopNewsArticle>?,
    newsManager: NewsManager
) {
    composable(BottomMenuScreen.TopNews.route) {
        TopNews(navHostController = navController, articles, newsManager.query, newsManager)
    }

    composable(BottomMenuScreen.Categories.route) {
        newsManager.onSelectedCategoryChanged("business")
        //newsManager.getArticlesByCategory("business")
        Categories(newsManager) {
            newsManager.onSelectedCategoryChanged(it)
            //newsManager.getArticlesByCategory(it)
        }
    }
    composable(BottomMenuScreen.Sources.route) {
        Sources(newsManager)
    }
}