package com.bitklog.presentation.properties

import androidx.lifecycle.ViewModel
import com.bitklog.core.common.R
import com.bitklog.domain.model.Property
import com.bitklog.domain.repository.PropertyRepository
import org.koin.core.annotation.Factory
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

/**
 * Won't use UseCases as there is not much logic that would go in there. Those would be
 * just a wrapper over the repository.
 */

@Factory
class PropertiesViewModel(private val propertyRepository: PropertyRepository) :
    ContainerHost<PropertiesUiState, PropertiesSideEffect>, ViewModel() {

    override val container = container<PropertiesUiState, PropertiesSideEffect>(PropertiesUiState())

    init {
        loadProperties()
    }

    fun onAction(action: PropertiesAction) {
        when (action) {
            PropertiesAction.Refresh -> loadProperties()
            is PropertiesAction.PropertySelected -> openProperty(action.propertyId)
        }
    }

    private fun loadProperties() = intent {
        if (state.isLoading) return@intent

        reduce {
            state.copy(
                isLoading = true,
                errorMessageResId = null
            )
        }

        propertyRepository.getProperties().fold(
            onSuccess = { properties ->
                reduce {
                    state.copy(
                        properties = properties,
                        isLoading = false,
                        errorMessageResId = null
                    )
                }
            },
            onFailure = { throwable ->
                reduce {
                    state.copy(
                        isLoading = false,
                        errorMessageResId = R.string.error_unable_load_properties
                    )
                }
            }
        )
    }

    private fun openProperty(propertyId: Int) = intent {
        postSideEffect(PropertiesSideEffect.NavigateToPropertyDetails(propertyId))
    }
}

data class PropertiesUiState(
    val properties: List<Property> = emptyList(), // could be mapped to a UI specific model, but in this case it doesn't make sense
    val isLoading: Boolean = false,
    val errorMessageResId: Int? = null // could be implemented to accept also String messages
)

sealed interface PropertiesAction {
    data object Refresh : PropertiesAction
    data class PropertySelected(val propertyId: Int) : PropertiesAction
}

sealed interface PropertiesSideEffect {
    data class NavigateToPropertyDetails(val propertyId: Int) : PropertiesSideEffect
}
