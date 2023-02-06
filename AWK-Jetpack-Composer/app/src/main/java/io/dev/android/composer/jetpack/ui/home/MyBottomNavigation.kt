package io.dev.android.composer.jetpack.ui.home

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import io.dev.android.composer.jetpack.R
import io.dev.android.composer.jetpack.ui.data.defaultContentDes

@Composable
fun MyBottomNavigationBar(modifier: Modifier = Modifier) {
    BottomNavigation(modifier) {
        BottomNavigationItem(
            selected = true,
            icon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_spa),
                    contentDescription = defaultContentDes
                )
            },
            label = {
                Text(text = stringResource(id = R.string.bottom_navigation_home))
            },
            onClick = {}
        )

        BottomNavigationItem(
            selected = false,
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = defaultContentDes
                )
            },
            label = {
                Text(text = stringResource(id = R.string.bottom_navigation_profile))
            },
            onClick = { }
        )
    }
}