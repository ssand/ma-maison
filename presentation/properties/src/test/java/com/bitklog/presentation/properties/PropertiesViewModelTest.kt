package com.bitklog.presentation.properties

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

class PropertiesViewModelTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()
    private val propertyRepository = mockk<PropertyRepository>()

    @Test
    fun `loadProperties success emits loading then data`() = runTest {
        coEvery { propertyRepository.getProperties() } returns Result.success(dummyProperties)
        val viewModel = PropertiesViewModel(propertyRepository)

        viewModel.test(this) {
            viewModel.onAction(PropertiesAction.Refresh)
            expectState(PropertiesUiState(isLoading = true))
            expectState(PropertiesUiState(properties = dummyProperties, isLoading = false))
            expectNoItems()
        }
    }

    @Test
    fun `loadProperties failure emits loading then error`() = runTest {
        coEvery { propertyRepository.getProperties() } returns Result.failure(Throwable("Network error"))
        val viewModel = PropertiesViewModel(propertyRepository)

        viewModel.test(this) {
            viewModel.onAction(PropertiesAction.Refresh)
            expectState(PropertiesUiState(isLoading = true))
            expectState(PropertiesUiState(errorMessageResId = R.string.error_unable_load_properties, isLoading = false))
            expectNoItems()
        }
    }

    @Test
    fun `property selection emits navigation side effect`() = runTest {
        coEvery { propertyRepository.getProperties() } returns Result.success(emptyList())
        val viewModel = PropertiesViewModel(propertyRepository)
        val propertyId = 42

        viewModel.test(this) {
            viewModel.onAction(PropertiesAction.PropertySelected(propertyId))
            expectSideEffect(PropertiesSideEffect.NavigateToPropertyDetails(propertyId))
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

private val dummyProperties = listOf(dummyProperty)
