package com.gafful.streche

import co.touchlab.kermit.Kermit
import co.touchlab.kermit.NSLogLogger
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import org.koin.dsl.module


actual val platformModule = module {
    single<SqlDriver> { NativeSqliteDriver(AppDatabase.Schema, "KampkitDb") }

    val baseKermit = Kermit(NSLogLogger()).withTag("KampKit")
    factory { (tag: String?) -> if (tag != null) baseKermit.withTag(tag) else baseKermit }
}
