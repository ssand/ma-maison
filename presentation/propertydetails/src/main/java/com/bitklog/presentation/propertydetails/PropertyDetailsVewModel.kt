package com.bitklog.presentation.propertydetails

import androidx.lifecycle.ViewModel
import com.bitklog.core.commonres.R
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
class PropertyDetailsVewModel(private val propertyRepository: PropertyRepository) :
    ContainerHost<PropertyDetailsUiState, Any>, ViewModel() {

    override val container = container<PropertyDetailsUiState, Any>(PropertyDetailsUiState())

    fun onAction(action: PropertyDetailsAction) {
        when (action) {
            is PropertyDetailsAction.LoadPropertyDetails -> loadPropertyDetails(action.propertyId)
        }
    }

    private fun loadPropertyDetails(propertyId: Int) = intent {
        if (state.isLoading && state.property != null) return@intent

        reduce {
            state.copy(
                isLoading = true,
                errorMessageResId = null
            )
        }

        propertyRepository.getProperty(propertyId).fold(
            onSuccess = { property ->
                reduce {
                    state.copy(
                        property = property,
                        isLoading = false,
                        errorMessageResId = null
                    )
                }
            },
            onFailure = {
                reduce {
                    state.copy(
                        isLoading = false,
                        errorMessageResId = R.string.error_unable_load_property
                    )
                }
            }
        )
    }

}

data class PropertyDetailsUiState(
    val property: Property? = null, // could be mapped to a UI specific model, but in this case it doesn't make sense
    val isLoading: Boolean = false,
    val errorMessageResId: Int? = null
)

sealed interface PropertyDetailsAction {
    data class LoadPropertyDetails(val propertyId: Int) : PropertyDetailsAction
}
