package com.gafful.streche

import com.gafful.streche.ktor.OpenTdbApi
import com.gafful.streche.opentdb.OpenTdb
import com.gafful.streche.response.BreedResult
import io.ktor.client.engine.*
import io.ktor.client.engine.mock.*


// TODO convert this to use Ktor's MockEngine
class KtorApiMock(mockEngine: MockEngine) : OpenTdbApi {

    val engine: HttpClientEngine = mockEngine

    private var nextResult: () -> BreedResult = { throw error("Uninitialized!") }
    var calledCount = 0
        private set

    override suspend fun getCategories(): OpenTdb.CategoryResponseDto {

        return OpenTdb.CategoryResponseDto(listOf(OpenTdb.CategoryDto(1, "hope")))
    }

    override suspend fun getTrivia(category: String, count: Int): OpenTdb.TriviaResponseDto {
        return emptyList<OpenTdb.TriviaResponseDto>()[0]
    }
}
