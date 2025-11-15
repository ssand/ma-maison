package com.bitklog.mamaison.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.bitklog.core.util.Destination
import com.bitklog.presentation.properties.PropertiesDestination
import com.bitklog.presentation.properties.properties
import com.bitklog.presentation.propertydetails.goToPropertyDetails
import com.bitklog.presentation.propertydetails.propertyDetails

@Composable
fun MaMaisonNavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    startDestination: Destination = PropertiesDestination,
    snackbarHostState: SnackbarHostState
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = startDestination
    ) {

        properties(
            onPropertyClicked = navHostController::goToPropertyDetails,
            snackbarHostState = snackbarHostState
        )

        propertyDetails(snackbarHostState = snackbarHostState)
    }
}
