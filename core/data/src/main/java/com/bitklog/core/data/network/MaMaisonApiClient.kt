package com.bitklog.core.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val BASE_URL = "gsl-apps-technical-test.dignp.com"
private const val TIME_OUT = 6_000 // millis

private val jsonSerializer: Json = Json {
    prettyPrint = true
    isLenient = true
    ignoreUnknownKeys = true
}

internal val ktorHttpClient: HttpClient = HttpClient(Android) {

    install(ContentNegotiation) {
        json(jsonSerializer)
    }

    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                co.touchlab.kermit.Logger.v(message)
            }
        }
        level = LogLevel.ALL
    }

    install(ResponseObserver) {
        onResponse { response ->
            co.touchlab.kermit.Logger.d(response.status.value.toString())
        }
    }

    install(DefaultRequest) {
        url {
            protocol = URLProtocol.HTTPS
            host = BASE_URL
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
    }

    engine {
        connectTimeout = TIME_OUT
        socketTimeout = TIME_OUT
    }
}
