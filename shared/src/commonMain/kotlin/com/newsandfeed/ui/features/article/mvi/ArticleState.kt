package com.newsandfeed.ui.features.home.mvi

import com.newsandfeed.domain.entity.ArticleEntity

data class ArticleState(
    val isLoading: Boolean = true,
    val error: String? = "",
    val showToastErrorIfNotConnectionInternet: Boolean = false,
    val articles:  ArticleEntity = ArticleEntity(content = emptyList())
) {

    constructor(): this(
       isLoading = true,
        showToastErrorIfNotConnectionInternet = false,
        error = null,
        articles = ArticleEntity(content = emptyList())
    )

}