package com.gafful.streche.ktor

import com.gafful.streche.opentdb.OpenTdb


interface OpenTdbApi {
    suspend fun getCategories(): OpenTdb.CategoryResponseDto // Flow
    suspend fun getTrivia(category: String, count: Int): OpenTdb.TriviaDto
}
