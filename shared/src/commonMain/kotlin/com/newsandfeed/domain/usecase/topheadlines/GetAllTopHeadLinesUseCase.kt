package com.newsandfeed.domain.usecase.topheadlines

import com.newsandfeed.domain.entity.ArticleEntity
import com.newsandfeed.domain.entity.DataOrException
import com.newsandfeed.domain.repository.TopHeadlinesRepository
import kotlinx.coroutines.flow.Flow

class GetAllTopHeadLinesUseCase(
    private val topHeadlinesRepository: TopHeadlinesRepository
) {

    operator fun invoke(): Flow<DataOrException<ArticleEntity?, Boolean, Exception>> =
        topHeadlinesRepository.getTopHeadlines()

}