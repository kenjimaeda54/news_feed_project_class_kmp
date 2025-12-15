package com.newsandfeed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.BottomAppBar
import com.newsandfeed.ui.features.screens.MainScreen
import com.newsandfeed.ui.features.screens.article.ArticleScreen
import com.newsandfeed.ui.theme.NewsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            NewsTheme {
                MainScreen()
            }
        }
    }
}
