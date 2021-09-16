package com.gafful.streche

class ScoreRepository(driverFactory: DatabaseDriverFactory) {
    private val database = Database(driverFactory)

    @Throws(Exception::class)
    suspend fun add(forceReload: Boolean) {
        database.insertLaunch(Launch(2L, "mission-name", 2030,
            "deetails", true, "GBC"))
    }

    @Throws(Exception::class)
    suspend fun getLaunches(forceReload: Boolean): List<Launch> {
        val cachedLaunches = database.getAllLaunches()
        return if (cachedLaunches.isNotEmpty() && !forceReload) {
            cachedLaunches
        } else {
            cachedLaunches
//            api.getAllLaunches().also {
//                database.clearDatabase()
//                database.createLaunches(it)
//            }
        }
    }
}