package com.newsandfeed.di

import com.newsandfeed.data.remote.RemoteDataSource
import com.newsandfeed.data.repository.ArticleRepositoryImpl
import com.newsandfeed.database.NewsDatabase
import com.newsandfeed.domain.repository.ArticleRepository
import com.newsandfeed.domain.usecase.article.GetAllArticleUseCase
import com.newsandfeed.domain.usecase.article.GetOnlyTopicArticleUseCase
import com.newsandfeed.infra.remote.KtorApi
import com.newsandfeed.infra.remote.KtorApiImpl
import com.newsandfeed.ui.features.home.ArticleViewModel
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
    )
}

private val clientModule = module {
    factory<KtorApi> { KtorApiImpl() }
    single { RemoteDataSource(get()) }
}

private val repositoryModule = module {
    single<ArticleRepository> { ArticleRepositoryImpl() }
}

private val useCaseModule = module {
    single { GetAllArticleUseCase(get()) }
    single { GetOnlyTopicArticleUseCase(get()) }
}

private val viewModelModule = module {
    single { ArticleViewModel() }
}

private val coreDatabase = module {
    single { NewsDatabase(get()) }
}

fun initKoin() = initKoin { }