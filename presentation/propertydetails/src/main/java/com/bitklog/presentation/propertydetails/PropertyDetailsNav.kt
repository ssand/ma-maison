package com.bitklog.presentation.propertydetails

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.bitklog.core.util.Destination
import kotlinx.serialization.Serializable

@Serializable
data class PropertyDetailsDestination(val propertyId: Int) : Destination

fun NavController.goToPropertyDetails(propertyId: Int, navOptions: NavOptions? = null) =
    this.navigate(
        route = PropertyDetailsDestination(propertyId),
        navOptions = navOptions
    )

fun NavGraphBuilder.propertyDetails(snackbarHostState: SnackbarHostState) {
    composable<PropertyDetailsDestination> { entry ->
        val propertyId = entry.toRoute<PropertyDetailsDestination>().propertyId
        PropertyDetailsRoute(
            propertyId = propertyId,
            snackbarHostState = snackbarHostState
        )
    }
}
