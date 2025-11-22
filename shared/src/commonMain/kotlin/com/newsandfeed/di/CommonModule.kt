package com.newsandfeed.di

import com.newsandfeed.data.remote.RemoteDataSource
import com.newsandfeed.data.repository.NewsRepositoryImpl
import com.newsandfeed.domain.repository.NewsRepository
import com.newsandfeed.infra.remote.KtorApi
import com.newsandfeed.infra.remote.KtorApiImpl
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration) = startKoin {
    appDeclaration()
    modules(
        clientModule
    )
}

private val clientModule = module {
    factory<KtorApi> { KtorApiImpl() }
    single { RemoteDataSource(get()) }
}

private val repositoryModule = module {
    single<NewsRepository>{ NewsRepositoryImpl() }
}

fun initKoin() = initKoin { }