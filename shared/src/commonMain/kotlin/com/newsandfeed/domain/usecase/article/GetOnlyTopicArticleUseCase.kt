package com.newsandfeed.domain.usecase.article

import com.newsandfeed.domain.entity.DataOrException
import com.newsandfeed.domain.entity.ArticleEntity
import com.newsandfeed.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow

class GetOnlyTopicArticleUseCase(
    private val articleRepository: ArticleRepository
) {

    operator fun invoke(word: String): Flow<DataOrException<ArticleEntity?, Boolean, Exception>> =
        articleRepository.getOnlyTopicArticles(word)

}