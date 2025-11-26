package com.newsandfeed.di

import com.newsandfeed.data.remote.RemoteDataSource
import com.newsandfeed.data.repository.NewsRepositoryImpl
import com.newsandfeed.domain.repository.NewsRepository
import com.newsandfeed.domain.usecase.home.GetCurrentNewsUseCase
import com.newsandfeed.infra.remote.KtorApi
import com.newsandfeed.infra.remote.KtorApiImpl
import com.newsandfeed.ui.features.home.HomeViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration) = startKoin {
    appDeclaration()
    modules(
        clientModule,
        repositoryModule,
        useCaseModule,
        viewModelModule
    )
}

private val clientModule = module {
    factory<KtorApi> { KtorApiImpl() }
    single { RemoteDataSource(get()) }
}

private val repositoryModule = module {
    single<NewsRepository> { NewsRepositoryImpl() }
}

private val useCaseModule = module {
    single { GetCurrentNewsUseCase(get()) }
}

private val viewModelModule = module {
    single { HomeViewModel() }
}

fun initKoin() = initKoin { }