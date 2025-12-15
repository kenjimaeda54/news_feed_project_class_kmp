package com.newsandfeed.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import co.touchlab.sqliter.DatabaseConfiguration
import com.newsandfeed.database.NewsDatabase
import org.koin.dsl.module

actual val driverSqlModule = module {
   single<SqlDriver>{
       NativeSqliteDriver(
           schema = NewsDatabase.Schema,
           name = "NewsDatabase.db",
           onConfiguration = { config: DatabaseConfiguration ->
               config.copy(
                   extendedConfig = DatabaseConfiguration.Extended(
                       foreignKeyConstraints = true
                   )
               )
           }
       )
   }
}