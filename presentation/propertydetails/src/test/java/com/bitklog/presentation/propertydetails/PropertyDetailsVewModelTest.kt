package com.bitklog.presentation.propertydetails

import com.bitklog.core.commonres.R
import com.bitklog.core.util.TestDispatcherRule
import com.bitklog.domain.model.Property
import com.bitklog.domain.repository.PropertyRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.orbitmvi.orbit.test.test

class PropertyDetailsVewModelTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()
    private val propertyRepository = mockk<PropertyRepository>()

    @Test
    fun `loadPropertyDetails success updates state with property`() = runTest {
        val propertyId = dummyProperty.id
        coEvery { propertyRepository.getProperty(propertyId) } returns Result.success(dummyProperty)
        val viewModel = PropertyDetailsVewModel(propertyRepository)

        viewModel.test(this) {
            viewModel.onAction(PropertyDetailsAction.LoadPropertyDetails(propertyId))
            expectState(PropertyDetailsUiState(isLoading = true))
            expectState(PropertyDetailsUiState(property = dummyProperty, isLoading = false))
            expectNoItems()
        }
    }

    @Test
    fun `loadPropertyDetails failure exposes error state`() = runTest {
        val propertyId = dummyProperty.id
        coEvery { propertyRepository.getProperty(propertyId) } returns Result.failure(Throwable("Network error"))
        val viewModel = PropertyDetailsVewModel(propertyRepository)

        viewModel.test(this) {
            viewModel.onAction(PropertyDetailsAction.LoadPropertyDetails(propertyId))
            expectState(PropertyDetailsUiState(isLoading = true))
            expectState(PropertyDetailsUiState(errorMessageResId = R.string.error_unable_load_property, isLoading = false))
            expectNoItems()
        }
    }
}

private val dummyProperty = Property(
    id = 1,
    area = 75.0,
    offerType = 0,
    city = "Paris",
    price = 350_000.0,
    propertyType = "Apartment",
    url = "https://example.com/property/1.jpg",
    professional = "John Doe",
    rooms = 3,
    bedrooms = 2
)
