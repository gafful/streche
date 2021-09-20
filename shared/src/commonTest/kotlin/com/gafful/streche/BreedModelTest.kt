package com.gafful.streche


import co.touchlab.kermit.Kermit
import com.gafful.streche.opentdb.Response
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BreedModelTest : BaseTest() {

    private var model: BreedModel = BreedModel()
    private var kermit = Kermit()
    private var testDbConnection = testDbConnection()
    private var dbHelper = DatabaseHelper(
        testDbConnection,
        kermit,
        Dispatchers.Default
    )
    val mockEngine = MockEngine { request ->
        //if (request)
        println("request")
        println(request)
        println(request.url.toString())
        respond(
            content = ByteReadChannel("""{"ip":"127.0.0.1"}"""),
            status = HttpStatusCode.OK,
            headers = headersOf(HttpHeaders.ContentType, "application/json")
        )
    }

    private val ktorApi = KtorApiMock(mockEngine)

    @BeforeTest
    fun setup() {
        appStart(dbHelper, ktorApi, kermit)
    }

    @Test
    fun testGetCategories() = runTest {
        val expectedError = DataState<Response.Category>(exception = null, empty = true)
        val actualError = model.getCategories(0L)

        assertEquals(
            expectedError,
            actualError
        )
    }

    @AfterTest
    fun breakdown() = runTest {
        testDbConnection.close()
        appEnd()
    }
}
