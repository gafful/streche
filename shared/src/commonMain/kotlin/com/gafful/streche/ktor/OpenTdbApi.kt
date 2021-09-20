package com.gafful.streche.ktor

import com.gafful.streche.opentdb.Response


interface OpenTdbApi {
    suspend fun getCategories(): List<Response.Category> // Flow?
    suspend fun getTrivia(category: String, count: Int): Response.Trivia
}
