package com.bitklog.core.data.datasource

import com.bitklog.core.data.model.PropertiesDto
import com.bitklog.core.data.model.PropertyDto

interface PropertyRemoteDs {

    suspend fun getProperties(): PropertiesDto

    suspend fun getProperty(propertyId: Int): PropertyDto
}
