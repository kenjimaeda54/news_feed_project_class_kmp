package com.newsandfeed.ui.features.topheadline.mvi

sealed class HomeIntent {
    data object LoadTopHeadlines : HomeIntent()
}