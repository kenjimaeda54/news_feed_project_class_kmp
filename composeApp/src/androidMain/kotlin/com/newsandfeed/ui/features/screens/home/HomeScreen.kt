package com.newsandfeed.ui.features.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.newsandfeed.ui.features.topheadline.HomeViewModel
import com.newsandfeed.ui.features.topheadline.mvi.HomeIntent
import com.newsandfeed.ui.features.views.CardComponent
import com.newsandfeed.ui.features.views.ErrorComponent
import com.newsandfeed.ui.features.views.LoadingComponent

@SuppressLint( "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen() {
    val homeViewModel = viewModel<HomeViewModel>()
    val state by homeViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        homeViewModel.handleIntent(HomeIntent.LoadTopHeadlines)
    }

    Scaffold { paddingValues ->
        if (state.isLoading) {
            LoadingComponent()
        }else if(state.error != null) {
            ErrorComponent()
        }else {
            LazyColumn(
                modifier = Modifier.padding(paddingValues)
            ) {
                items(state.topHeadlines.content, key = { it.url }) { article ->
                    CardComponent(article)
                }
            }
        }
    }
}