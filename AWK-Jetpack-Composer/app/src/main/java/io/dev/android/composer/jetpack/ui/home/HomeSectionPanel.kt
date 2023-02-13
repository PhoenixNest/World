package io.dev.android.composer.jetpack.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeSectionPanel(
    @StringRes titleResId: Int,
    modifier: Modifier = Modifier,
    contentView: @Composable () -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = titleResId),
            style = TextStyle(fontSize = 14.sp),
            modifier = Modifier
                .paddingFromBaseline(
                    top = 40.dp,
                    bottom = 8.dp
                )
                .padding(8.dp)
        )
        contentView()
    }
}