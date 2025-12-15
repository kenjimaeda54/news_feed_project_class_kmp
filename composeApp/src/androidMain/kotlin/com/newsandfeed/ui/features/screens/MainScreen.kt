package com.newsandfeed.ui.features.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.newsandfeed.ui.route.BottomCustomNavigation
import com.newsandfeed.ui.route.BottomScreens
import com.newsandfeed.ui.route.NavGraphApp

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        bottomBar = {
            BottomCustomNavigation(
                navHostController = navController,
                navDestination = navBackStackEntry?.destination
            )
        }
    ) { paddingValues ->
        NavGraphApp(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }

}