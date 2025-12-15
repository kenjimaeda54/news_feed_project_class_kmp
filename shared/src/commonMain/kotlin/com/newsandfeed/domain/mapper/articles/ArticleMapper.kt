package com.newsandfeed.domain.mapper.articles

import com.newsandfeed.data.dto.ArticleDto
import com.newsandfeed.data.dto.NewsDto
import com.newsandfeed.domain.entity.ArticleEntity
import com.newsandfeed.domain.entity.ContentEntity


fun NewsDto.toDomain() = ArticleEntity(
    content = articles.map { it.toDomain() }
)

fun ArticleDto.toDomain() = ContentEntity(
    author = author,
    content = content,
    description = description,
    title = title,
    url = url,
    urlToImage = urlToImage
)