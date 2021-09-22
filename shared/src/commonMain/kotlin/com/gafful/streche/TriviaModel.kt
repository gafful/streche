package com.gafful.streche

import co.touchlab.kermit.Kermit
import com.gafful.streche.ktor.OpenTdbApi
import com.gafful.streche.opentdb.OpenTdb
import com.gafful.streche.opentdb.toTriviaVo
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.serialization.decodeValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class TriviaModel : KoinComponent {
    private val dbHelper: DatabaseHelper by inject()
    private val settings: Settings by inject()
    private val openTdbApi: OpenTdbApi by inject()
    private val log: Kermit by inject { parametersOf("BreedModel") }

    companion object {
        internal const val DB_TIMESTAMP_KEY = "DbTimestampKey"
    }

    init {
//        ensureNeverFrozen()
    }

    @ExperimentalSettingsApi
    @ExperimentalSerializationApi
    fun initSession(): Flow<TriviaViewState> = flow {
        /**
         * If trivia, get next one
         * else check If categories
         * if categories, fetch trivia
         * else request categories
         */

        log.v { "initSession:" }
        dbHelper.getAllTrivia().collect { listItems ->
            if (listItems.toList().isEmpty()) {
                log.v { "isEmpty:" }
                val categories = settings.decodeValue(
                    ListSerializer(OpenTdb.CategoryDto.serializer()),
                    "categories",
                    emptyList())
                log.v { "categories: $categories" }
                if (categories.isEmpty()) {
                    emit(TriviaViewState.FetchingCategories)
                    val categoriesResponse = openTdbApi.getCategories()
                    println("categoriesResponse: $categoriesResponse")

//                    settings.encodeValue(ListSerializer(String.serializer()),
//                        "categories",
//                        emptyList())
                    emit(TriviaViewState.RequestCategories(categoriesResponse.triviaCategories))
                } else {
                    emit(TriviaViewState.RequestCategories(categories))
                }
            } else {
                log.v { "isNOT Empty:" }
                log.v { "itemList:" }
                log.v { "itemList: $listItems" }
                emit(TriviaViewState.ShowTrivia(listItems.map { it.toTriviaVo() }))
            }
        }
    }
}
