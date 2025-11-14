package com.bitklog.presentation.propertydetails

import androidx.lifecycle.ViewModel
import com.bitklog.domain.model.Property

/**
 * Won't use UseCases as there is not much logic that would go in there. Those would be
 * just a wrapper over the repository.
 */
class PropertyDetailsVewModel() : ViewModel() {
}

data class PropertyDetailsUiState(
    val property: Property? = null, // could be mapped to a UI specific model, but in this case it doesn't make sense
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
)

sealed interface PropertyDetailsAction {
    data class LoadRepoDetails(val repoId: Int) : PropertyDetailsAction
}
