package com.newsandfeed.domain.entity

data class ArticleEntity(
    val content: List<ContentEntity>,
)

data class ContentEntity(
    val author: String,
    val content: String,
    val description: String,
    val title: String,
    val url: String,
    val urlToImage: String
)