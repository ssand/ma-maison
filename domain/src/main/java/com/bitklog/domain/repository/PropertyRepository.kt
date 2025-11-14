package com.bitklog.domain.repository

import com.bitklog.domain.model.Property

interface PropertyRepository {

    suspend fun getProperties(): Result<List<Property>>

    suspend fun getProperty(propertyId: Int): Result<Property>
}
