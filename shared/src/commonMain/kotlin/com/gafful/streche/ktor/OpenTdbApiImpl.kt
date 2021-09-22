package com.gafful.streche.ktor


import co.touchlab.kermit.Kermit
import com.gafful.streche.opentdb.OpenTdb
import io.ktor.client.HttpClient
import io.ktor.client.request.*
import io.ktor.http.takeFrom

class OpenTdbApiImpl(log: Kermit, client: HttpClient) : OpenTdbApi {

    // If this is a constructor property, then it gets captured
    // inside HttpClient config and freezes this whole class.
    @Suppress("CanBePrimaryConstructorProperty")
    private val log = log

    @Suppress("CanBePrimaryConstructorProperty")
    private val client = client

    init {
        //ensureNeverFrozen()
    }

    private fun HttpRequestBuilder.opentdbapi(path: String) {
        url {
            takeFrom("https://opentdb.com/")
            encodedPath = path
        }
    }

    override suspend fun getCategories(): OpenTdb.CategoryResponseDto {
        log.d { "Fetching categories" }
        return client.get {
            opentdbapi("api_category.php")
        }
    }

    override suspend fun getTrivia(category: String, count: Int): OpenTdb.TriviaDto {
        log.d { "Fetching trivia from opentdb.com" }
        return client.get {
            opentdbapi("api.php")
            parameter("amount", count)
            parameter("category", category)
        }
    }
}
