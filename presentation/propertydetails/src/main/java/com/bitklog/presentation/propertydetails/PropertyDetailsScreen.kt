package com.bitklog.presentation.propertydetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bed
import androidx.compose.material.icons.outlined.HomeWork
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MeetingRoom
import androidx.compose.material.icons.outlined.Straighten
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.bitklog.core.commonres.R
import com.bitklog.core.designsystem.component.ErrorContent
import com.bitklog.core.designsystem.component.ProgressOverlay
import com.bitklog.core.designsystem.theme.Dimen
import com.bitklog.core.designsystem.theme.MaMaisonTheme
import com.bitklog.core.util.toCurrencyAmount
import com.bitklog.domain.model.Property
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun PropertyDetailsRoute(
    propertyId: Int,
    snackbarHostState: SnackbarHostState,
    viewModel: PropertyDetailsVewModel = koinViewModel()
) {
    val uiState by viewModel.collectAsState()

    LaunchedEffect(propertyId) {
        viewModel.onAction(PropertyDetailsAction.LoadPropertyDetails(propertyId))
    }

    val errorMessage = uiState.errorMessageResId?.let {
        stringResource(it)
    }

    LaunchedEffect(errorMessage) {
        errorMessage?.let { snackbarHostState.showSnackbar(it) }
    }

    PropertyDetailsScreen(
        state = uiState,
        onRefresh = { viewModel.onAction(PropertyDetailsAction.LoadPropertyDetails(propertyId)) }
    )
}

@Composable
fun PropertyDetailsScreen(
    state: PropertyDetailsUiState,
    onRefresh: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            state.isLoading -> ProgressOverlay()

            state.errorMessageResId != null || state.property == null ->
                ErrorContent(
                    onRefresh = onRefresh,
                    errorText = stringResource(R.string.error_something_wrong),
                    buttonText = stringResource(R.string.retry),
                    modifier = Modifier.align(Alignment.Center)
                )

            else ->
                PropertyDetailsContent(
                    property = state.property,
                    modifier = Modifier.fillMaxSize()
                )
        }
    }
}

@Composable
fun PropertyDetailsContent(
    property: Property,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier.verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(Dimen.padding_m)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            model = property.url,
            contentScale = ContentScale.Crop,
            contentDescription = property.city
        )

        Column(
            modifier = Modifier.padding(Dimen.padding_m),
            verticalArrangement = Arrangement.spacedBy(Dimen.padding_s)
        ) {
            Text(
                text = property.price.toCurrencyAmount(),
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.primary
            )

            PropertyBaseInfo(Icons.Outlined.LocationOn, property.city)

            property.propertyType?.let { type ->
                PropertyBaseInfo(Icons.Outlined.HomeWork, type)
            }
        }

        HorizontalDivider(modifier = Modifier.padding(horizontal = Dimen.padding_m))

        Card(modifier = Modifier.padding(horizontal = Dimen.padding_m)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimen.padding_m),
                verticalArrangement = Arrangement.spacedBy(Dimen.padding_m)
            ) {
                Text(
                    text = stringResource(id = R.string.property_highlights),
                    style = MaterialTheme.typography.titleMedium
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    PropertySecondaryInfo(
                        icon = Icons.Outlined.Straighten,
                        label = stringResource(R.string.property_area),
                        value = stringResource(R.string.property_area_value, property.area)
                    )

                    PropertySecondaryInfo(
                        icon = Icons.Outlined.MeetingRoom,
                        label = stringResource(R.string.property_rooms),
                        value = property.rooms?.toString() ?: "-"
                    )

                    PropertySecondaryInfo(
                        icon = Icons.Outlined.Bed,
                        label = stringResource(R.string.property_bedrooms),
                        value = property.bedrooms?.toString() ?: "-"
                    )
                }
            }
        }

        property.professional?.let { professional ->
            Card(modifier = Modifier.padding(horizontal = Dimen.padding_m)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimen.padding_m),
                    verticalArrangement = Arrangement.spacedBy(Dimen.padding_s)
                ) {
                    Text(
                        text = stringResource(R.string.property_contact),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = professional,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Composable
private fun PropertyBaseInfo(
    icon: ImageVector,
    label: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.size(Dimen.padding_xs))

        Text(
            text = label,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun PropertySecondaryInfo(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Dimen.padding_xs)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(name = "Property Details - Success", showBackground = true)
@Composable
private fun PropertyDetailsSuccessPreview() {
    MaMaisonTheme {
        PropertyDetailsScreen(
            state = PropertyDetailsUiState(
                property = previewProperty,
                isLoading = false
            ),
            onRefresh = {}
        )
    }
}

@Preview(name = "Property Details - Error", showBackground = true)
@Composable
private fun PropertyDetailsErrorPreview() {
    MaMaisonTheme {
        PropertyDetailsScreen(
            state = PropertyDetailsUiState(
                property = null,
                isLoading = false,
                errorMessageResId = R.string.error_something_wrong
            ),
            onRefresh = {}
        )
    }
}

private val previewProperty = Property(
    id = 1,
    area = 120.0,
    offerType = 0,
    city = "Paris",
    price = 985_000.0,
    propertyType = "Apartment",
    url = "https://images.unsplash.com/photo-1505691938895-1758d7feb511",
    professional = "John Doe",
    rooms = 5,
    bedrooms = 3
)
