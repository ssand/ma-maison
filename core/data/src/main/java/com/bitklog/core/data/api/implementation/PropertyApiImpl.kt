package com.bitklog.core.data.api.implementation

import com.bitklog.core.data.api.PropertyApi
import com.bitklog.core.data.model.PropertiesDto
import com.bitklog.core.data.model.PropertyDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import org.koin.core.annotation.Factory

@Factory
class PropertyApiImpl(private val httpClient: HttpClient) : PropertyApi {

    override suspend fun getProperties(): PropertiesDto =
        httpClient.get {
            url { appendPathSegments("listings.json") }
        }.body()

    override suspend fun getProperty(propertyId: Int): PropertyDto =
        httpClient.get {
            url { appendPathSegments("listings", "$propertyId.json") }
        }.body()
}
