package com.newsandfeed.infra.remote

import io.ktor.client.HttpClient

interface KtorApi {
    val httpClient: HttpClient
}