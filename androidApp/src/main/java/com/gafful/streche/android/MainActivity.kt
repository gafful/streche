package com.gafful.streche.android

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import co.touchlab.kermit.Kermit
import com.gafful.streche.Greeting
import com.gafful.streche.TriviaViewState
import com.gafful.streche.opentdb.CategoryVo
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity(), KoinComponent {
    private val viewModel: SetupViewModel by viewModel()
    private val log: Kermit by inject { parametersOf("MainActivity") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)
        tv.text = greet()

        log.v { "trivia state flow: ${viewModel.triviaStateFlow.value}" }
        when (viewModel.triviaStateFlow.value) {
            is TriviaViewState.Initial -> {
                log.v { "izz initial" }
            }
            is TriviaViewState.FetchingCategories -> {
                log.v { "izz fetc" }
            }
            is TriviaViewState.RequestCategories -> {
                log.v { "izz req cat" }
            }
            is TriviaViewState.ShowTrivia -> {
                log.v { "izz show" }
            }
            else -> {
                println(" we don't know ")
            }
        }

//        viewModel.onCategoriesSelected(listOf(
//            CategoryVo(32, "14"),
//            CategoryVo(32, "15"),
//            CategoryVo(32, "32"),
//            CategoryVo(31, "31"),
//        )
//        )

        viewModel.onTriviaAnswered(2L,"answer")
    }
}
