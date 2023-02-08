package io.dev.android.composer.jetpack.ui.home.state_change_list

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.dev.android.composer.jetpack.ui.data.defaultContentDes

@Composable
fun StateListItem(
    title: String,
    modifier: Modifier = Modifier,
    checked: Boolean,
    onItemClick: () -> Unit = {},
    onCheckChange: (Boolean) -> Unit = {},
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )

        Checkbox(
            checked = checked,
            onCheckedChange = onCheckChange
        )

        IconButton(onClick = onItemClick) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = defaultContentDes
            )
        }
    }
}