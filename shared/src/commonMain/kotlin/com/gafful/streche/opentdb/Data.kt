package com.gafful.streche.opentdb

import com.gafful.streche.Trivia
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class OpenTdb {
    @Serializable
    data class CategoryResponseDto(
        @SerialName("trivia_categories")
        val triviaCategories: List<CategoryDto>,
    )

    @Serializable
    data class CategoryDto(val id: Int, val name: String)

    @Serializable
    data class TriviaResponseDto(
        @SerialName("response_code")
        val responseCode: Int,
        val results: List<TriviaDto>,
    )

    @Serializable
    data class TriviaDto(
        val category: String,
        val type: String,
        val difficulty: String,
        val question: String,
        @SerialName("correct_answer")
        val correctAnswer: String,
        @SerialName("incorrect_answers")
        val incorrectAnswers: List<String>,
    )

    enum class ResponseCodes(i: Int) {
        SUCCESS(0),

        //Could not return results. The API doesn't have enough questions for your query.
        // (Ex. Asking for 50 Questions in a Category that only has 20.)
        NO_RESULTS(1),

        //  Contains an invalid parameter. Arguements passed in aren't valid. (Ex. Amount = Five)
        INVALID_PARAMETER(2),

        // Session Token does not exist.
        TOKEN_NOT_FOUND(3),

        // Session Token has returned all possible questions for the specified query.
        // Resetting the Token is necessary.
        TOKEN_EMPTY(4),
    }
}

@Serializable
data class CategoryVo(val id: Int, val name: String)

class WillFry {
    //data class CategoryDto(val id: Int, val name: String)
    data class TriviaDto(
        val category: String,
        val type: String,
        val question: String,
        val correctAnswer: String,
        val incorrectAnswers: List<String>,
    )
}

// category, difficulty,
//enum class Difficulty

fun Trivia.toTriviaVo() = TriviaVo(
    this.category,
    this.type,
    this.difficulty,
    this.question,
    this.correct_answer,
    this.incorrect_answers
)

fun OpenTdb.TriviaDto.toTriviaVo() = TriviaVo(
    this.category,
    this.type,
    this.difficulty,
    this.question,
    this.correctAnswer,
    this.incorrectAnswers,
)

fun Trivia.toTriviaDto() = OpenTdb.TriviaDto(
    this.category,
    this.type,
    this.difficulty,
    this.question,
    this.correct_answer,
    this.incorrect_answers,
)

data class TriviaVo(
    val category: String,
    val type: String,
    val difficulty: String,
    val question: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>,
)