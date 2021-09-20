package com.gafful.streche

import co.touchlab.kermit.Kermit
import com.gafful.streche.ktor.OpenTdbApi
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.withTimeout
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

fun appStart(helper: DatabaseHelper, ktorApi: OpenTdbApi, log: Kermit) {
    val coreModule = module {
        single { helper }
        single { ktorApi }
        single { log }
    }

    startKoin { modules(coreModule) }
}

fun appEnd() {
    stopKoin()
}

// Await with a timeout
suspend fun <T> Deferred<T>.await(timeoutMillis: Long) =
    withTimeout(timeoutMillis) { await() }

internal expect fun testDbConnection(): SqlDriver
