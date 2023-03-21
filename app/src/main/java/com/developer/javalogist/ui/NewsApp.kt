package com.developer.javalogist.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.developer.javalogist.components.BottomMenu
import com.developer.javalogist.model.TopNewsArticle
import com.developer.javalogist.network.NewsManager
import com.developer.javalogist.ui.screen.Categories
import com.developer.javalogist.ui.screen.DetailScreen
import com.developer.javalogist.ui.screen.Sources
import com.developer.javalogist.ui.screen.TopNews

@Composable
fun NewsApp() {
    val navController = rememberNavController()
    val scrollState = rememberScrollState()
    MainScreen(navController = navController, scrollState = scrollState)
}

@Composable
fun MainScreen(navController: NavHostController, scrollState: ScrollState) {
    Scaffold(
        bottomBar = { BottomMenu(navController = navController) }
    ) {
        Navigation(navController, scrollState, it)
    }
}

@Composable
fun Navigation(
    navController: NavHostController,
    scrollState: ScrollState,
    paddingValues: PaddingValues,
    newsManager: NewsManager = NewsManager(),
) {

    val articlesFromApi = newsManager.newsResponse.value.articles
    articlesFromApi?.let { articles ->
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
                    val article = articles[ind]
                    DetailScreen(navController, article, scrollState)
                }

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
        TopNews(navHostController = navController, articles)
    }

    composable(BottomMenuScreen.Categories.route) {
        newsManager.onSelectedCategoryChanged("business")
        newsManager.getArticlesByCategory("business")
        Categories(newsManager) {
            newsManager.onSelectedCategoryChanged(it)
            newsManager.getArticlesByCategory(it)
        }
    }
    composable(BottomMenuScreen.Sources.route) {
        Sources(newsManager)
    }
}