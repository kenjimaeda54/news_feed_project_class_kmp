package com.newsandfeed.domain.usecase.home

import com.newsandfeed.domain.entity.DataOrException
import com.newsandfeed.domain.entity.NewsEntity
import com.newsandfeed.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetCurrentNewsUseCase(
    private val newsRepository: NewsRepository
) {

    operator fun invoke(): Flow<DataOrException<NewsEntity?, Boolean, Exception>> =
        newsRepository.getAllCurrentNews()

}