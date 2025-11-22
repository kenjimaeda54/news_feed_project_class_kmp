package com.newsandfeed.ui.features.home.mvi

import com.newsandfeed.domain.entity.NewsEntity
import com.newsandfeed.domain.repository.NewsRepository

data class HomeState(
    val isLoading: Boolean = false,
    val error: String? = "",
    val currentNews:  NewsEntity = NewsEntity(articles = emptyList())
)