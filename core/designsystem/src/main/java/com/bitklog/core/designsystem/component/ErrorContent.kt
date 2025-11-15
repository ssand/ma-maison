package com.bitklog.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bitklog.core.designsystem.theme.Dimen

@Composable
fun ErrorContent(
    onRefresh: () -> Unit,
    errorText: String,
    buttonText: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = errorText,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(Dimen.padding_s))

        Button(onClick = onRefresh) {
            Text(text = buttonText)
        }
    }
}
