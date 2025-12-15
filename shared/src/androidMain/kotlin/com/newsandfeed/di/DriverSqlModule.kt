package com.newsandfeed.di

import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.newsandfeed.database.NewsDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual val driverSqlModule = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            schema = NewsDatabase.Schema,
            context = get(),
            name = "NewsDatabase.db",
            callback = object : AndroidSqliteDriver.Callback(NewsDatabase.Schema) {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    db.setForeignKeyConstraintsEnabled(true)
                }
            }
        )
    }
}