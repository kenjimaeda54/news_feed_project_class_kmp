package com.newsandfeed.di

import com.newsandfeed.util.ConnectivityObserver
import org.koin.dsl.module

actual val connectivityModule = module {
    single {
        ConnectivityObserver()
    }

}