package com.newsandfeed.ui.features.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
    val snackBarHostState = remember { SnackbarHostState() }
    val state by homeViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        homeViewModel.handleIntent(HomeIntent.LoadTopHeadlines)
    }

    LaunchedEffect(state.showToastErrorIfNotConnectionInternet) {
        if (state.showToastErrorIfNotConnectionInternet) {
            val result = snackBarHostState.showSnackbar(
                message = "Conecta com a internet e tenta novamente para visualizar conteúdo recente",
                actionLabel = "Tentar novamente",
                duration = SnackbarDuration.Long
            )

            if (result == SnackbarResult.ActionPerformed) {
                homeViewModel.handleIntent(HomeIntent.LoadTopHeadlines)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { paddingValues ->
        if (state.isLoading) {
            LoadingComponent()
        }else if(state.error != null && !state.showToastErrorIfNotConnectionInternet) {
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