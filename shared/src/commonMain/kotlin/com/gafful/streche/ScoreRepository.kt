//package com.gafful.streche
//
//class ScoreRepository(driverFactory: DatabaseDriverFactory) {
////    private val database = Database(driverFactory)
//
//    @Throws(Exception::class)
//    suspend fun add(forceReload: Boolean) {
////        database.insertTrivia(Trivia(null, "mission-name", 2030,
////            "deetails", true, "GBC"))YYYY-MM-DD HH:MM:SS
//    }
//
//    @Throws(Exception::class)
//    suspend fun getAllTrivia(forceReload: Boolean): List<Trivia> {
//        return emptyList()
////        val cachedLaunches = database.getAllLaunches()
////        return if (cachedLaunches.isNotEmpty() && !forceReload) {
////            cachedLaunches
////        } else {
////            cachedLaunches
//////            api.getAllLaunches().also {
//////                database.clearDatabase()
//////                database.createLaunches(it)
//////            }
////        }
//    }
//}