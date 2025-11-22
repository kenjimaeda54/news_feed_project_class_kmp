package com.newsandfeed.domain.mapper.articles

import com.newsandfeed.data.dto.ArticleDto
import com.newsandfeed.data.dto.NewsDto
import com.newsandfeed.domain.entity.ArticleEntity
import com.newsandfeed.domain.entity.NewsEntity


fun NewsDto.toDomain() = NewsEntity(
    articles = articles.map { it.toDomain() }
)

fun ArticleDto.toDomain() = ArticleEntity(
    author = author,
    content = content,
    description = description,
    title = title,
    url = url,
    urlToImage = urlToImage
)