package com.newsandfeed.di

import com.newsandfeed.data.local.ArticleLocalDataSource
import com.newsandfeed.data.local.TopHeadlineLocalDataSource
import com.newsandfeed.data.remote.RemoteDataSource
import com.newsandfeed.data.repository.ArticleRepositoryImpl
import com.newsandfeed.data.repository.TopHeadlinesRepositoryImpl
import com.newsandfeed.database.NewsDatabase
import com.newsandfeed.domain.repository.ArticleRepository
import com.newsandfeed.domain.repository.TopHeadlinesRepository
import com.newsandfeed.domain.usecase.article.GetAllArticleUseCase
import com.newsandfeed.domain.usecase.article.GetOnlyTopicArticleUseCase
import com.newsandfeed.domain.usecase.topheadlines.GetAllTopHeadLinesUseCase
import com.newsandfeed.infra.remote.KtorApi
import com.newsandfeed.infra.remote.KtorApiImpl
import com.newsandfeed.ui.features.article.ArticleViewModel
import com.newsandfeed.ui.features.topheadline.HomeViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration) = startKoin {
    appDeclaration()
    modules(
        clientModule,
        repositoryModule,
        useCaseModule,
        viewModelModule,
        coreDatabase,
        driverSqlModule,
        localModule,
        connectivityModule
    )
}

private val clientModule = module {
    factory<KtorApi> { KtorApiImpl() }
    single { RemoteDataSource(get()) }
}

private val localModule = module {
    single { ArticleLocalDataSource() }
    single { TopHeadlineLocalDataSource() }
}

private val repositoryModule = module {
    single<ArticleRepository> { ArticleRepositoryImpl() }
    single<TopHeadlinesRepository> { TopHeadlinesRepositoryImpl() }
}

private val useCaseModule = module {
    single { GetAllArticleUseCase(get()) }
    single { GetOnlyTopicArticleUseCase(get()) }
    single { GetAllTopHeadLinesUseCase(get()) }
}

private val viewModelModule = module {
    single { ArticleViewModel() }
    single { HomeViewModel() }
}

private val coreDatabase = module {
    single { NewsDatabase(get()) }
}

fun initKoin() = initKoin { }