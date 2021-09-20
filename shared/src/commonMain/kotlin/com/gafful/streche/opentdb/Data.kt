package com.gafful.streche.opentdb

class Response {
    data class Category(val id: Int, val name: String)
    data class Trivia(
        val category: String,
        val type: String,
        val difficulty: String,
        val question: String,
        val correctAnswer: String,
        val incorrectAnswers: List<String>,
    )
}