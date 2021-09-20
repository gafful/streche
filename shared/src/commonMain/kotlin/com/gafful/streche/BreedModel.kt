package com.gafful.streche

import co.touchlab.kermit.Kermit
import com.gafful.streche.ktor.OpenTdbApi
import com.gafful.streche.opentdb.Response
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class BreedModel : KoinComponent {
    private val dbHelper: DatabaseHelper by inject()
    private val ktorApi: OpenTdbApi by inject()
    private val log: Kermit by inject { parametersOf("BreedModel") }

    companion object {
        internal const val DB_TIMESTAMP_KEY = "DbTimestampKey"
    }

    init {
//        ensureNeverFrozen()
    }

    suspend fun getCategories(currentTimeMS: Long): DataState<Response.Category> {
        return try {
            val result = ktorApi.getCategories()
            log.v { "Breed network result: ${result}" }
            DataState<Response.Category>(empty = true)
//            log.v { "Breed network result: ${result.status}" }
//            val breedList = result.message.keys.sorted().toList()
//            log.v { "Fetched ${breedList.size} breeds from network" }
//            settings.putLong(DB_TIMESTAMP_KEY, currentTimeMS)
//            if (breedList.isEmpty()) {
//                DataState<ItemDataSummary>(empty = true)
//            } else {
//                DataState<ItemDataSummary>(
//                    ItemDataSummary(
//                        null,
//                        breedList.map { Breed(0L, it, 0L) }
//                    )
//                )
//            }
        } catch (e: Exception) {
            log.e(e) { "Error downloading breed list" }
            DataState<Response.Category>(exception = "Unable to download breed list")
        }
    }
}