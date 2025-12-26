package com.newsandfeed.ui.features.home.mvi

sealed class ArticleIntent {
    data object LoadIAllArticles: ArticleIntent()
    data class SearchArticle(val query: String): ArticleIntent()
}