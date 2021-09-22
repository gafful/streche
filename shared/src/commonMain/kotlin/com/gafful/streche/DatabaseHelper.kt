package com.gafful.streche


import co.touchlab.kermit.Kermit
import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

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
}
