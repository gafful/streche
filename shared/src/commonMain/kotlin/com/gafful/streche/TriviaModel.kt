package com.gafful.streche

import co.touchlab.kermit.Kermit
import com.gafful.streche.ktor.OpenTdbApi
import com.gafful.streche.opentdb.CategoryVo
import com.gafful.streche.opentdb.OpenTdb
import com.gafful.streche.opentdb.toTriviaVo
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.serialization.decodeValue
import com.russhwolf.settings.serialization.encodeValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock
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
    private val clock: Clock by inject()

    companion object {
        internal const val DB_TIMESTAMP_KEY = "DbTimestampKey"
    }

    init {
//        ensureNeverFrozen()
    }

    @ExperimentalSettingsApi
    @ExperimentalSerializationApi
    fun initSession(): Flow<TriviaViewState> = flow {
        log.v { "initSession:" }
        //TODO: Get say 50 at a time
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

//    fun fetchTrivia(categories: List<CategoryVo>): Flow<TriviaViewState> = flow {
//
//    }

    @ExperimentalSettingsApi
    @ExperimentalSerializationApi
    suspend fun onCategoriesSelected(categories: List<CategoryVo>): Flow<TriviaViewState> = flow {

        println("onCategoriesSelected1: $categories")
        emit(TriviaViewState.FetchingTrivia)
        settings.encodeValue(
            ListSerializer(OpenTdb.CategoryDto.serializer()),
            "categories",
            emptyList())

        //TODO Restrict to 5 at a time
        val results: MutableList<OpenTdb.TriviaDto> = mutableListOf()

        for (it in categories) {
            val triviaResponse = openTdbApi.getTrivia(it.name, 5)
            when (triviaResponse.responseCode) {
                0 -> {
                    //println(triviaResponse)
                    results.addAll(triviaResponse.results)
                }
                1 -> {
                    //println(triviaResponse)
                    results.addAll(triviaResponse.results)
                }
                2 -> {
                    //println(triviaResponse)
                    results.addAll(triviaResponse.results)
                }
                3 -> {
                    //println(triviaResponse)
                    results.addAll(triviaResponse.results)
                }
                4 -> {
                    //println(triviaResponse)
                    results.addAll(triviaResponse.results)
                }
                else -> {

                }
            }
            kotlinx.coroutines.delay(500)
        }
        dbHelper.insertTrivia(results)
        emit(TriviaViewState.ShowTrivia(results.map { it.toTriviaVo() }))
    }

    fun onTriviaAnswered(id: Long, answer: String): Flow<TriviaViewState> = flow{
        dbHelper.updateTrivia(id, answer, clock.now())
    }
}
