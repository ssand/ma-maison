package com.bitklog.core.data.datasource.implementation

import com.bitklog.core.data.datasource.PropertyRemoteDs
import com.bitklog.core.data.model.PropertiesDto
import com.bitklog.core.data.model.PropertyDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import org.koin.core.annotation.Factory

@Factory
class PropertyRemoteDsImpl(private val apiClient: HttpClient) : PropertyRemoteDs {

    override suspend fun getProperties(): PropertiesDto =
        apiClient.get {
            url { appendPathSegments("listings.json") }
        }.body()

    override suspend fun getProperty(propertyId: Int): PropertyDto =
        apiClient.get {
            url { appendPathSegments("$propertyId.json") }
        }.body()
}
