package com.developer.javalogist.ui

import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.developer.javalogist.ui.screen.DetailScreen
import com.developer.javalogist.ui.screen.TopNews

@Composable
fun NewsApp() {
    Navigation()
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val scrollState = rememberScrollState()
    NavHost(navController = navController, startDestination = "TopNews") {
        composable("TopNews") {
            TopNews(navController)
        }

        composable(
            "DetailScreen/{newsId}",
            arguments = listOf(navArgument("newsId") { type = NavType.IntType })
        ) {
            val newsData = MockData.getNews(it.arguments?.getInt("newsId"))
            DetailScreen(newsData, scrollState)
        }
    }
}