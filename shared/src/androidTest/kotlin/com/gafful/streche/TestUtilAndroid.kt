package com.gafful.streche

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver

internal actual fun testDbConnection(): SqlDriver {
    return JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).apply {
        AppDatabase.Schema.create(this)
    }
}
