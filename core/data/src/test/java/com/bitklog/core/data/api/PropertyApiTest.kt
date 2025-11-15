package com.bitklog.core.data.api

import com.bitklog.core.data.api.implementation.PropertyApiImpl
import com.bitklog.core.data.model.PropertiesDto
import com.bitklog.core.data.util.dummyPropertyDto
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test

class PropertyApiTest {

    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun `getProperties hits listings endpoint and parses response`() = runTest {
        val expected = PropertiesDto(totalCount = 1, items = listOf(dummyPropertyDto))
        val engine = MockEngine { request ->
            assertEquals("/listings.json", request.url.encodedPath)
            jsonResponse(json.encodeToString(expected))
        }

        val api = createApi(engine)
        val response = api.getProperties()

        assertEquals(expected, response)
    }

    @Test
    fun `getProperty hits property endpoint and parses response`() = runTest {
        val engine = MockEngine { request ->
            assertEquals("/listings/${dummyPropertyDto.id}.json", request.url.encodedPath)
            jsonResponse(json.encodeToString(dummyPropertyDto))
        }

        val api = createApi(engine)
        val response = api.getProperty(dummyPropertyDto.id)

        assertEquals(dummyPropertyDto, response)
    }

    private fun createApi(engine: MockEngine): PropertyApi =
        PropertyApiImpl(
            HttpClient(engine) {
                install(ContentNegotiation) {
                    json(json)
                }
            }
        )

    private fun MockRequestHandleScope.jsonResponse(body: String) =
        respond(
            content = body,
            headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        )
}
