package com.gafful.streche


import app.cash.turbine.test
import co.touchlab.kermit.Kermit
import com.gafful.streche.opentdb.CategoryVo
import com.gafful.streche.opentdb.toTriviaDto
import com.gafful.streche.opentdb.toTriviaVo
import com.russhwolf.settings.MockSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TriviaModelTest : BaseTest() {

    private var model: TriviaModel = TriviaModel()
    private var kermit = Kermit()
    private var testDbConnection = testDbConnection()
    private val settings = MockSettings()
    private val ktorApi = KtorApiMock()
    private val clock = ClockMock(Clock.System.now())
    private var dbHelper = DatabaseHelper(
        testDbConnection,
        listOfStringsAdapter,
        kermit,
        Dispatchers.Default
    )

    @BeforeTest
    fun setup() {
        appStart(dbHelper, settings, ktorApi, kermit, clock)
    }

    @OptIn(FlowPreview::class)
    @Test
    fun initializeSessionWhenDatabaseHasNoTrivia() {
        val response = ktorApi.categorySuccessResponse()
        ktorApi.prepareResult(response)

        runTest {
            model.initSession()
                .test {
                    //assertEquals(TriviaViewState.FetchingCategories, expectItem())
                    assertEquals(TriviaViewState.RequestCategories(response.triviaCategories),
                        expectItem())
                    cancel()
                }
        }
    }

    @OptIn(FlowPreview::class)
    @Test
    fun initializeSessionWhenDatabaseHasTrivia() {
        val response = ktorApi.triviaSuccessResponse()
        ktorApi.prepareResult(response)

        runTest {
            val trivia = response.results
            dbHelper.insertTrivia(trivia)

            model.initSession()
                .test {
                    assertEquals(TriviaViewState.ShowTrivia(response.results.map { it.toTriviaVo() }),
                        expectItem())
                    cancel()
                }
        }
    }


    @OptIn(FlowPreview::class)
    @Test
    fun fetchTriviaWhenCategoriesAreSelected() {
        val categoryList = listOf(CategoryVo(1, "Wan"))
        val response = ktorApi.triviaSuccessResponse()
        ktorApi.prepareResult(response)

        runTest {
            flowOf(model.onCategoriesSelected(categoryList))
                .flattenMerge()
                .test {
                    assertEquals(TriviaViewState.FetchingTrivia, expectItem())
                    assertEquals(
                        TriviaViewState.ShowTrivia(response.results.map { it.toTriviaVo() }),
                        expectItem())
                    //assertEquals(TriviaViewState.FetchingTrivia, expectComplete())
                    expectComplete()
                    cancel()
                    //cancelAndIgnoreRemainingEvents()
                }
        }
    }

    @OptIn(FlowPreview::class)
    @Test
    fun saveTriviaInDatabaseWhenCategoriesAreSelected() {
        val categoryList = listOf(CategoryVo(1, "Wan"))
        val response = ktorApi.triviaSuccessResponse()
        ktorApi.prepareResult(response)

        runTest {
            model.onCategoriesSelected(categoryList)
            val job = launch {
                model.getAllTrivia().collectLatest { it ->
                    assertEquals(response.results, it.map { it.toTriviaDto() })
                }
            }
            job.cancel()
        }
    }

    @OptIn(FlowPreview::class)
    @Test
    fun updateTriviaWhenAnswered() {
        runTest {
            val trivia = ktorApi.triviaSuccessResponse().results
            dbHelper.insertTrivia(trivia)

            // Index will always be 1 since it's an in-memory database
            model.onTriviaAnswered(1L, "Trues")
            val triviaById = model.getTriviaById(1L)
            assertEquals(triviaById?.answer, "Trues")
            dbHelper.getAllTrivia()
        }
    }

    @AfterTest
    fun breakdown() = runTest {
        testDbConnection.close()
        appEnd()
    }
}
