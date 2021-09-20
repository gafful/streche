package com.gafful.streche


import co.touchlab.kermit.Kermit
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

//TODO: Change to DatabaseService
class DatabaseHelper(
    sqlDriver: SqlDriver,
    private val log: Kermit,
    private val backgroundDispatcher: CoroutineDispatcher
) {
    private val dbRef: AppDatabase = AppDatabase(sqlDriver)
}
