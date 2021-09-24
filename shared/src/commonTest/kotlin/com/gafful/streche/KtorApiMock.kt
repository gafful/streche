package com.gafful.streche

import com.gafful.streche.ktor.OpenTdbApi
import com.gafful.streche.opentdb.OpenTdb


// TODO convert this to use Ktor's MockEngine
class KtorApiMock : OpenTdbApi {
    //val engine: HttpClientEngine = mockEngine

    private var nextCategoriesResult: () -> OpenTdb.CategoryResponseDto =
        { throw error("Uninitialized!") }

    private var nextTriviaResult: () -> OpenTdb.TriviaResponseDto =
        { throw error("Uninitialized!") }

    var calledCount = 0
        private set

    override suspend fun getCategories(): OpenTdb.CategoryResponseDto {
        val result = nextCategoriesResult()
        calledCount++
        return result
    }

    override suspend fun getTrivia(category: String, count: Int): OpenTdb.TriviaResponseDto {
        val result = nextTriviaResult()
        calledCount++
        return result
    }

    fun categorySuccessResponse(): OpenTdb.CategoryResponseDto {
        val categories = listOf(OpenTdb.CategoryDto(1, "Wan"))
        return OpenTdb.CategoryResponseDto(categories)
    }

    fun triviaSuccessResponse(): OpenTdb.TriviaResponseDto {
        val trivia = OpenTdb.TriviaDto(
            "geography",
            "multiple choice",
            "hard",
            "how are you?",
            "mi gud man",
            listOf("I dey!", "cool things!")
        )
        return OpenTdb.TriviaResponseDto(0, listOf(trivia))
    }

    fun prepareResult(result: OpenTdb.TriviaResponseDto) {
        nextTriviaResult = { result }
    }

    fun prepareResult(result: OpenTdb.CategoryResponseDto) {
        nextCategoriesResult = { result }
    }

    fun throwOnCall(throwable: Throwable) {
        nextCategoriesResult = { throw throwable }
    }
}
