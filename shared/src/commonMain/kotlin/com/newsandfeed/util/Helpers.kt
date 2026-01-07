package com.newsandfeed.util

import kotlinx.coroutines.flow.first
import kotlin.time.Clock
import kotlin.time.Duration.Companion.minutes
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

const val HAS_NOT_CONNECTION_INTERNET = "Sem conexão com a internet"

@OptIn(ExperimentalTime::class)
fun isExpiredData(createAt: String, expirationTime: Int): Boolean {
    val expirationLimit = expirationTime.minutes
    return try {
        //dado salvo no timestamp 2025-12-18 10:42:27
        val isoString = createAt.replace(" ", "T") + "Z"
        //convertendo para ios
        val createAtInstant = Instant.parse(isoString)
        val currentInstant = Clock.System.now()
        val timeElapsed = currentInstant - createAtInstant
        timeElapsed > expirationLimit
    } catch (exception: IllegalArgumentException) {
        false
    } catch (exception: Exception) {
        false
    }
}