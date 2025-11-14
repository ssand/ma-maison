package com.bitklog.presentation.properties

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitklog.core.util.Destination
import kotlinx.serialization.Serializable

@Serializable
object PropertiesDestination : Destination

fun NavGraphBuilder.properties(
    onPropertyClicked: (Int) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    composable<PropertiesDestination> {
        PropertiesRoute(
            onPropertyClicked = onPropertyClicked,
            snackbarHostState = snackbarHostState
        )
    }
}
