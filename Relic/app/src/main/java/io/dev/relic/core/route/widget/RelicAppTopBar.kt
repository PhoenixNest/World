package io.dev.relic.core.route.widget

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource

@Composable
fun RelicTopAppBar(
    @StringRes titleRes: Int,
    actionBarIcon: ImageVector,
    modifier: Modifier = Modifier,
    onActionBarIconClick: () -> Unit = {}
) {
    TopAppBar(
        title = { Text(text = stringResource(id = titleRes)) },
        actions = {
            IconButton(onClick = onActionBarIconClick) {
                Icon(imageVector = actionBarIcon, contentDescription = null)
            }
        },
        modifier = modifier.fillMaxWidth()
    )
}
