package com.newsandfeed.infra.remote

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val BASE_URL = "https://newsapi.org"

class KtorApiImpl: KtorApi {

    override val httpClient: HttpClient = HttpClient {
        defaultRequest {
            url(BASE_URL)
            url {
                protocol = URLProtocol.HTTPS
            }
        }
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    prettyPrint = true
                }
            )
        }
    }

}