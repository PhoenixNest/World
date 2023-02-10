package io.dev.android.composer.jetpack.ui.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.dev.android.composer.jetpack.R
import io.dev.android.composer.jetpack.ui.data.defaultContentDes

@Composable
fun SearchBar(
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        leadingIcon = {
            Icon(
                // You can use the built-in icon by Icons.Default.*
                imageVector = Icons.Default.Search,
                contentDescription = defaultContentDes
            )

            // ===== OR =====

            Icon(
                // Load ui resources by resources id
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = defaultContentDes
            )
        },
        placeholder = {
            Text(text = stringResource(id = R.string.search_placeholder))
        },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp),
    )
}