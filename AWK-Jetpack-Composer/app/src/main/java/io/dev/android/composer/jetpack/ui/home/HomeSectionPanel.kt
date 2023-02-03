package io.dev.android.composer.jetpack.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun HomeSectionPanel(
    @StringRes titleResId: Int,
    modifier: Modifier = Modifier,
    contentView: @Composable () -> Unit
) {
    Column(modifier = modifier) {
        Text(text = stringResource(id = titleResId))
        contentView()
    }
}