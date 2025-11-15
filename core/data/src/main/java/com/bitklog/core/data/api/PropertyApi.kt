package com.bitklog.core.data.api

import com.bitklog.core.data.model.PropertiesDto
import com.bitklog.core.data.model.PropertyDto

interface PropertyApi {

    suspend fun getProperties(): PropertiesDto

    suspend fun getProperty(propertyId: Int): PropertyDto
}
