package com.bitklog.core.data.datasource

import com.bitklog.core.data.api.PropertyApi
import com.bitklog.core.data.datasource.implementation.PropertyRemoteDsImpl
import com.bitklog.core.data.model.PropertiesDto
import com.bitklog.core.data.util.dummyPropertyDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class PropertyRemoteDsImplTest {

    val fakeApi: PropertyApi = mockk()

    @Test
    fun `getProperties delegates to PropertyApi`() = runTest {
        val expectedResponse = PropertiesDto(totalCount = 1, items = listOf(dummyPropertyDto))
        coEvery { fakeApi.getProperties() } returns (expectedResponse)

        val remoteDs = PropertyRemoteDsImpl(fakeApi)
        val response = remoteDs.getProperties()

        Assert.assertEquals(expectedResponse, response)
        coVerify(exactly = 1) { fakeApi.getProperties() }
    }

    @Test
    fun `getProperty delegates to PropertyApi with correct id`() = runTest {
        coEvery { fakeApi.getProperty(dummyPropertyDto.id) } returns (dummyPropertyDto)

        val remoteDs = PropertyRemoteDsImpl(fakeApi)
        val response = remoteDs.getProperty(dummyPropertyDto.id)

        Assert.assertEquals(dummyPropertyDto, response)
        coVerify(exactly = 1) { fakeApi.getProperty(dummyPropertyDto.id) }
    }
}
