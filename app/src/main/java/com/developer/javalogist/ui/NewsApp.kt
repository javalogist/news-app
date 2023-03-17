package com.developer.javalogist.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.developer.javalogist.components.BottomMenu
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
    paddingValues: PaddingValues
) {

    NavHost(navController = navController, startDestination = "top_news") {
        bottomNavigation(navController = navController)
        composable("top_news") {
            TopNews(navController)
        }

        composable(
            "DetailScreen/{newsId}",
            arguments = listOf(navArgument("newsId") { type = NavType.IntType })
        ) {
            val newsData = MockData.getNews(it.arguments?.getInt("newsId"))
            DetailScreen(navController, newsData, scrollState)
        }
    }
}


fun NavGraphBuilder.bottomNavigation(navController: NavHostController) {
    composable(BottomMenuScreen.TopNews.route) {
        TopNews(navHostController = navController)
    }

    composable(BottomMenuScreen.Categories.route) {
        Categories()
    }
    composable(BottomMenuScreen.Sources.route) {
        Sources()
    }
}