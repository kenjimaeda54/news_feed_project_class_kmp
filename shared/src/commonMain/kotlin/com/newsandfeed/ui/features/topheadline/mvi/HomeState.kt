package com.newsandfeed.ui.features.topheadline.mvi

import com.newsandfeed.domain.entity.ArticleEntity

data class HomeState (
    val isLoading: Boolean = false,
    val error: String? = "",
    val showToastErrorIfNotConnectionInternet: Boolean = false,
    val topHeadlines: ArticleEntity = ArticleEntity(content = listOf())
) {

    constructor(): this(
    isLoading = false,
    showToastErrorIfNotConnectionInternet = false,
    error = null,
    topHeadlines = ArticleEntity(content = listOf())
    )

}