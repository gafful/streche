package com.gafful.streche

import com.gafful.streche.opentdb.OpenTdb
import com.gafful.streche.opentdb.TriviaVo

data class DataState<out T>(
    val data: T? = null,
    val exception: String? = null,
    val empty: Boolean = false,
    val loading: Boolean = false,
)

sealed class TriviaViewState {
    object Initial : TriviaViewState()
    object FetchingCategories : TriviaViewState()
    data class RequestCategories(val categories: List<OpenTdb.CategoryDto>) : TriviaViewState()
    data class ShowTrivia(val categories: List<TriviaVo>) : TriviaViewState()
}
