package com.bitklog.presentation.properties

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.bitklog.core.common.R
import com.bitklog.core.designsystem.component.ProgressOverlay
import com.bitklog.core.designsystem.theme.Dimen
import com.bitklog.core.designsystem.theme.MaMaisonTheme
import com.bitklog.core.util.toCurrencyAmount
import com.bitklog.domain.model.Property
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun PropertiesRoute(
    onPropertyClicked: (Int) -> Unit,
    snackbarHostState: SnackbarHostState,
    viewModel: PropertiesViewModel = koinViewModel()
) {
    val uiState by viewModel.collectAsState()

    viewModel.collectSideEffect { effect ->
        when (effect) {
            is PropertiesSideEffect.NavigateToPropertyDetails -> onPropertyClicked(effect.propertyId)
        }
    }

    val errorMessage = uiState.errorMessageResId?.let {
        stringResource(it)
    }

    LaunchedEffect(errorMessage) {
        errorMessage?.let { snackbarHostState.showSnackbar(it) }
    }

    PropertiesScreen(
        state = uiState,
        onRefresh = { viewModel.onAction(PropertiesAction.Refresh) },
        onPropertyClicked = { id -> viewModel.onAction(PropertiesAction.PropertySelected(id)) }
    )
}

@Composable
fun PropertiesScreen(
    state: PropertiesUiState,
    onRefresh: () -> Unit,
    onPropertyClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        when {
            state.isLoading && state.properties.isEmpty() ->
                ProgressOverlay()

            state.errorMessageResId != null && state.properties.isEmpty() ->
                ErrorContent(
                    onRefresh = onRefresh,
                    modifier = Modifier.align(Alignment.Center)
                )

            else ->
                PropertyList(
                    properties = state.properties,
                    onPropertyClicked = onPropertyClicked,
                    modifier = Modifier.fillMaxSize()
                )
        }
    }
}

@Composable
private fun PropertyList(
    properties: List<Property>,
    onPropertyClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Dimen.padding_s),
        contentPadding = PaddingValues(all = Dimen.padding_m),
    ) {
        items(
            items = properties,
            key = { property -> property.id }
        ) { property ->
            PropertyItem(
                property = property,
                onClick = { onPropertyClicked(property.id) }
            )
        }
    }
}

@Composable
private fun PropertyItem(
    property: Property,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimen.padding_m),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PropertyThumbnail(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(Dimen.radius_m)),
                imageUrl = property.url,
                contentDescription = property.city
            )

            Spacer(modifier = Modifier.size(Dimen.padding_m))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = property.city,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                property.propertyType?.let {
                    Spacer(modifier = Modifier.size(Dimen.padding_s))
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.size(Dimen.padding_s))

                Text(
                    text = property.price.toCurrencyAmount(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

// Will consider that all imageUrls are valid. No error place holder for now.
@Composable
private fun PropertyThumbnail(
    imageUrl: String?,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    if (imageUrl.isNullOrBlank())
        ThumbnailPlaceholder(modifier = modifier)
    else
        AsyncImage(
            modifier = modifier,
            model = imageUrl,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop
        )
}

@Composable
private fun ErrorContent(
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.error_something_wrong),
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(Dimen.padding_s))

        Button(onClick = onRefresh) {
            Text(text = stringResource(R.string.retry))
        }
    }
}

@Composable
private fun ThumbnailPlaceholder(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.background(MaterialTheme.colorScheme.surfaceContainerLow),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Home,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(name = "Properties - Content", showBackground = true)
@Composable
private fun PropertiesScreenContentPreview() {
    MaMaisonTheme {
        PropertiesScreen(
            state = PropertiesUiState(
                properties = previewProperties
            ),
            onRefresh = {},
            onPropertyClicked = {}
        )
    }
}

@Preview(name = "Properties - Error", showBackground = true)
@Composable
private fun PropertiesScreenErrorPreview() {
    MaMaisonTheme {
        PropertiesScreen(
            state = PropertiesUiState(errorMessageResId = R.string.error_unable_load_properties),
            onRefresh = {},
            onPropertyClicked = {}
        )
    }
}

private val previewProperties = listOf(
    Property(
        id = 1,
        area = 110.0,
        offerType = 0,
        city = "Paris",
        price = 725_000.0,
        propertyType = "Apartment",
        url = "https://v.seloger.com/s/crop/590x330/visuels/1/7/t/3/17t3fitclms3bzwv8qshbyzh9dw32e9l0p0udr80k.jpg"
    ),
    Property(
        id = 2,
        area = 85.0,
        offerType = 1,
        city = "Lyon",
        price = 495_000.0,
        propertyType = "Loft",
        url = "https://v.seloger.com/s/crop/590x330/visuels/2/a/l/s/2als8bgr8sd2vezcpsj988mse4olspi5rfzpadqok.jpg"
    )
)
