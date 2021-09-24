package com.gafful.streche

import co.touchlab.kermit.Kermit
import com.gafful.streche.ktor.OpenTdbApi
import com.gafful.streche.opentdb.CategoryVo
import com.gafful.streche.opentdb.OpenTdb
import com.gafful.streche.opentdb.toTriviaVo
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock
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

    fun initSession(): Flow<TriviaViewState> = flow {
        log.d { "Initialize session:" }
        //TODO: Get say 50 at a time
        getAllTrivia().collect { listItems ->
            if (listItems.toList().isEmpty()) {
                log.i { "No trivia in database:" }
                val categoriesResponse = openTdbApi.getCategories()
                emit(TriviaViewState.RequestCategories(categoriesResponse.triviaCategories))
            } else {
                emit(TriviaViewState.ShowTrivia(listItems.map { it.toTriviaVo() }))
            }
        }
    }

    suspend fun onCategoriesSelected(categories: List<CategoryVo>): Flow<TriviaViewState> = flow {
        log.d { "On categories selected: $categories" }
        emit(TriviaViewState.FetchingTrivia)

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
            // TODO: Restore?
            //kotlinx.coroutines.delay(500)
        }
        // TODO: Extract
        dbHelper.insertTrivia(results)
        emit(TriviaViewState.ShowTrivia(results.map { it.toTriviaVo() }))
    }

    suspend fun onTriviaAnswered(id: Long, answer: String)  {
        // TODO: Exception or nothing!
        dbHelper.updateTrivia(id, answer, clock.now())
    }

    // TODO: Move to repository?
     fun getAllTrivia(): Flow<List<Trivia>> {
        return dbHelper.getAllTrivia()
    }

    // TODO: Move to repository?
    suspend fun getTriviaById(id: Long): Trivia? {
        return dbHelper.getTriviaById(id)
    }
}
