package com.newsandfeed.domain.mapper.articles

import com.newsandfeed.data.dto.ArticleDto
import com.newsandfeed.data.dto.NewsDto
import com.newsandfeed.database.SelectAllArticleWithContent
import com.newsandfeed.database.SelectTopHeadlinesWithContentTopHeadlines
import com.newsandfeed.domain.entity.ArticleEntity
import com.newsandfeed.domain.entity.ContentEntity


fun NewsDto.toDomain() = ArticleEntity(
    content = articles.map { it.toDomain() }
)

fun ArticleDto.toDomain() = ContentEntity(
    author = author ?: "",
    content = content,
    description = description,
    title = title ?: "",
    url = url,
    urlToImage = urlToImage
)

fun SelectAllArticleWithContent.toDomain() = ContentEntity(
    author = this.author ?: "",
    content = this.content,
    description = this.description ?: "",
    title = this.title,
    url = this.url,
    urlToImage = this.urlToImage ?: ""
)

fun SelectTopHeadlinesWithContentTopHeadlines.toDomain() = ContentEntity(
    author = this.author ?: "",
    content = this.content,
    description = this.description ?: "",
    title = this.title ?: "",
    url = this.url,
    urlToImage = this.urlToImage ?: ""
)