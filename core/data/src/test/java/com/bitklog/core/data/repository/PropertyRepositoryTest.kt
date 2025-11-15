package com.bitklog.core.data.repository

import com.bitklog.core.data.datasource.PropertyRemoteDs
import com.bitklog.core.data.model.PropertiesDto
import com.bitklog.core.data.util.dummyProperty
import com.bitklog.core.data.util.dummyPropertyDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PropertyRepositoryImplTest {

    private val propertyRemoteDs = mockk<PropertyRemoteDs>()
    private val repository = PropertyRepositoryImpl(propertyRemoteDs)

    @Test
    fun `getProperties returns mapped domain list on success`() = runTest {
        val propertiesDto = PropertiesDto(
            totalCount = 1,
            items = listOf(dummyPropertyDto)
        )
        coEvery { propertyRemoteDs.getProperties() } returns propertiesDto

        val result = repository.getProperties()

        assertTrue(result.isSuccess)
        assertEquals(listOf(dummyProperty), result.getOrNull())
    }

    @Test
    fun `getProperties returns failure when data source fails`() = runTest {
        val error = RuntimeException("network error")
        coEvery { propertyRemoteDs.getProperties() } throws error

        val result = repository.getProperties()

        assertTrue(result.isFailure)
        assertEquals(error, result.exceptionOrNull())
    }

    @Test
    fun `getProperty returns mapped domain model on success`() = runTest {
        coEvery { propertyRemoteDs.getProperty(dummyPropertyDto.id) } returns dummyPropertyDto

        val result = repository.getProperty(dummyPropertyDto.id)

        assertTrue(result.isSuccess)
        assertEquals(dummyProperty, result.getOrNull())
    }

    @Test
    fun `getProperty returns failure when data source fails`() = runTest {
        val error = RuntimeException("network error")
        coEvery { propertyRemoteDs.getProperty(dummyPropertyDto.id) } throws error

        val result = repository.getProperty(dummyPropertyDto.id)

        assertTrue(result.isFailure)
        assertEquals(error, result.exceptionOrNull())
    }
}
