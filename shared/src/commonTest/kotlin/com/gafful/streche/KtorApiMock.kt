package com.gafful.streche

import com.gafful.streche.ktor.OpenTdbApi
import com.gafful.streche.opentdb.Response
import com.gafful.streche.response.BreedResult
import io.ktor.client.engine.*
import io.ktor.client.engine.mock.*
import io.ktor.utils.io.*


// TODO convert this to use Ktor's MockEngine
class KtorApiMock(mockEngine: MockEngine) : OpenTdbApi {

    val engine: HttpClientEngine = mockEngine

    private var nextResult: () -> BreedResult = { throw error("Uninitialized!") }
    var calledCount = 0
        private set

    override suspend fun getCategories(): List<Response.Category> {

        return listOf(Response.Category(1, "hope"))
    }

    override suspend fun getTrivia(category: String, count: Int): Response.Trivia {
        return emptyList<Response.Trivia>().get(0)
    }
}
