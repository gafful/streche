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
}

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
enum class Difficulty

fun Trivia.toTriviaVo() = TriviaVo(
    this.category,
    this.type,
    this.difficulty,
    this.question,
    this.correct_answer,
    this.incorrect_answers
)

data class TriviaVo(
    val category: String,
    val type: String,
    val difficulty: String,
    val question: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>,
)