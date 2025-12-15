package com.newsandfeed.ui.route

import androidx.annotation.DrawableRes
import com.newsandfeed.R

sealed class BottomBarScreen(val route: String, @DrawableRes val icon: Int, val label: String) {
    object Home : BottomBarScreen("home", label = "Ínicio", icon = R.drawable.home)
    object Articles : BottomBarScreen("article", R.drawable.article, "Artigos")

}

class BottomScreens {
    companion object {
        fun screens(): List<BottomBarScreen> = listOf(
            BottomBarScreen.Home,
            BottomBarScreen.Articles
        )
    }
}