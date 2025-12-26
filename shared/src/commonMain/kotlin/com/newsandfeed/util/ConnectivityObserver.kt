package com.newsandfeed.util

import kotlinx.coroutines.flow.Flow

enum class NetWorkStatus {
    Available,
    Unavailable,
}

expect class ConnectivityObserver {
    fun observe(): Flow<NetWorkStatus>
}