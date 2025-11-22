package com.newsandfeed.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class NewsDto(
    val articles: List<ArticleDto>,
)