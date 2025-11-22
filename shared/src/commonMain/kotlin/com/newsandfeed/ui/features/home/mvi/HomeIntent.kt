package com.newsandfeed.ui.features.home.mvi

sealed class HomeIntent {
    data object LoadItems: HomeIntent()
}