package com.gafful.streche

import com.gafful.streche.ktor.OpenTdbApi
import com.gafful.streche.ktor.OpenTdbApiImpl
import com.squareup.sqldelight.ColumnAdapter
import kotlinx.coroutines.Dispatchers
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope
import org.koin.dsl.module

//@Suppress("CanBePrimaryConstructorProperty")
//private val log = log

fun initKoin(appModule: Module): KoinApplication {
    val koinApplication = startKoin {
        modules(
            appModule,
            platformModule,
            coreModule
        )
    }

    return koinApplication
}

val listOfStringsAdapter = object : ColumnAdapter<List<String>, String> {
    override fun decode(databaseValue: String) =
        if (databaseValue.isEmpty()) {
            listOf()
        } else {
            databaseValue.split(",")
        }
    override fun encode(value: List<String>) = value.joinToString(separator = ",")
}

private val coreModule = module {
    single {
        DatabaseHelper(
            get(),
            listOfStringsAdapter,
            getWith("DatabaseHelper"),
            Dispatchers.Default
        )
    }
    single<OpenTdbApi> {
        OpenTdbApiImpl(
            getWith("OpenTdbApiImpl"),
            HttpClients(getWith("OpenTdbApiImpl")).client
        )
    }
//    single<Clock> {
//        Clock.System
//    }
}

internal inline fun <reified T> Scope.getWith(vararg params: Any?): T {
    return get(parameters = { parametersOf(*params) })
}

expect val platformModule: Module
