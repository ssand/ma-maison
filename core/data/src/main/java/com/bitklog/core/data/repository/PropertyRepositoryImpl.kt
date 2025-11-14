package com.bitklog.core.data.repository

import com.bitklog.core.data.datasource.PropertyRemoteDs
import com.bitklog.core.data.mapper.toDomain
import com.bitklog.domain.model.Property
import com.bitklog.domain.repository.PropertyRepository
import org.koin.core.annotation.Factory

@Factory
class PropertyRepositoryImpl(private val propertyRemoteDs: PropertyRemoteDs) : PropertyRepository {

    override suspend fun getProperties(): Result<List<Property>> =
        runCatching {
            propertyRemoteDs.getProperties().items.map { it.toDomain() }
        }

    override suspend fun getProperty(propertyId: Int): Result<Property> =
        runCatching {
            propertyRemoteDs.getProperty(propertyId).toDomain()
        }
}
