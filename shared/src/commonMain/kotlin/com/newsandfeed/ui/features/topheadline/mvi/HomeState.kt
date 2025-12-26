package com.newsandfeed.ui.features.topheadline.mvi

import com.newsandfeed.domain.entity.ArticleEntity

data class HomeState (
    val isLoading: Boolean = false,
    val error: String? = "",
    val topHeadlines: ArticleEntity = ArticleEntity(content = listOf())
) {

    constructor(): this(
    isLoading = false,
    error = null,
    topHeadlines = ArticleEntity(content = listOf())
    )

}