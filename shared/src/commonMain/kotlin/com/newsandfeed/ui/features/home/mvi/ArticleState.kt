package com.newsandfeed.ui.features.home.mvi

import com.newsandfeed.domain.entity.ArticleEntity

data class ArticleState(
    val isLoading: Boolean = true,
    val error: String? = "",
    val articles:  ArticleEntity = ArticleEntity(content = emptyList())
) {

    constructor(): this(
       isLoading = true,
        error = null,
        articles = ArticleEntity(content = emptyList())
    )

}