//package com.gafful.streche
//
//internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
//    private val database = AppDatabase(databaseDriverFactory.createDriver())
//    private val dbQuery = database.appDatabaseQueries
//
//    internal fun clearDatabase() {
//        dbQuery.transaction {
//            dbQuery.removeAllTrivia()
//        }
//    }
//
//    internal fun getAllLaunches(): List<Trivia> {
//        return dbQuery.selectAllTrivia().executeAsList()
//    }
//
//    internal fun insertTrivia(trivia: Trivia) {
//        dbQuery.insertTrivia(
//            id = null,
//            category = trivia.category,
//            type = trivia.type,
//            difficulty = trivia.difficulty,
//            question = trivia.question,
//            correct_answer = trivia.correct_answer,
//            incorrect_answers = trivia.incorrect_answers,
//            source = trivia.source,
//            answer = trivia.answer,
//            answered_on = trivia.answered_on,//Only this level may know about db and thus what to pass, not the callsite?
//        )
//    }
//}