package com.bitklog.core.data.datasource.implementation

import com.bitklog.core.data.api.PropertyApi
import com.bitklog.core.data.datasource.PropertyRemoteDs
import com.bitklog.core.data.model.PropertiesDto
import com.bitklog.core.data.model.PropertyDto
import org.koin.core.annotation.Factory

@Factory
class PropertyRemoteDsImpl(private val propertyApi: PropertyApi) : PropertyRemoteDs {

    override suspend fun getProperties(): PropertiesDto =
        propertyApi.getProperties()

    override suspend fun getProperty(propertyId: Int): PropertyDto =
        propertyApi.getProperty(propertyId)
}
