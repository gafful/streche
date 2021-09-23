package com.gafful.streche


import co.touchlab.kermit.Kermit
import com.gafful.streche.opentdb.OpenTdb
import com.gafful.streche.sqldelight.transactionWithContext
import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import io.ktor.util.date.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.datetime.Instant

//TODO: Change to DatabaseService
class DatabaseHelper(
    sqlDriver: SqlDriver,
    incorrectAnswersAdapter: ColumnAdapter<List<String>, String>,
    private val log: Kermit,
    private val backgroundDispatcher: CoroutineDispatcher
) {
    private val dbRef: AppDatabase = AppDatabase(sqlDriver, Trivia.Adapter(incorrectAnswersAdapter))

    fun getAllTrivia(): Flow<List<Trivia>> =
        dbRef.appDatabaseQueries
            .selectAllTrivia()
            .asFlow()
            .mapToList()
            .flowOn(backgroundDispatcher)

    suspend fun insertTrivia(items: List<OpenTdb.TriviaDto>) {
        log.d { "Inserting ${items.size} trivia into database" }
        dbRef.transactionWithContext(backgroundDispatcher) {
            items.forEach { trivia ->
                dbRef.appDatabaseQueries.insertTrivia(
                    null,
                    trivia.category,
                    trivia.type,
                    trivia.difficulty,
                    trivia.question,
                    trivia.correctAnswer,
                    trivia.incorrectAnswers,
                    "OPENTDB",
                    null,
                    null,
                )
            }
        }
    }

    suspend fun updateTrivia(id: Long, answer: String, time: Instant) {
        log.i { "Trivia $id: Answer $answer" }
        dbRef.transactionWithContext(backgroundDispatcher) {
            dbRef.appDatabaseQueries.updateTrivia(answer, time.toString(), id)
        }
    }
}
