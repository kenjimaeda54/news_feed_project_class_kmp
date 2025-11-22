package com.newsandfeed.domain.entity

data class NewsEntity(
    val articles: List<ArticleEntity>,
)

data class ArticleEntity(
    val author: String,
    val content: String,
    val description: String,
    val title: String,
    val url: String,
    val urlToImage: String
)