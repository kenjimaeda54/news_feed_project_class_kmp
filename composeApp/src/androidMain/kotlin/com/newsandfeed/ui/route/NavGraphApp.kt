package com.newsandfeed.ui.route

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.newsandfeed.ui.features.screens.article.ArticleScreen
import com.newsandfeed.ui.features.screens.home.HomeScreen

@Composable
fun NavGraphApp(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = BottomBarScreen.Home.route,
        modifier = modifier
    ) {
       composable(BottomBarScreen.Home.route) {
           HomeScreen()
       }
        composable(BottomBarScreen.Articles.route) {
            ArticleScreen()
        }
    }
}