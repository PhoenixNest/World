package io.dev.relic.feature.pages.detail.gallery.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.core.ui.dialog.ComposeBottomSheetDialog
import io.core.ui.theme.RelicAppTheme
import io.core.ui.theme.RelicFontFamily.googleSans
import io.module.media.R

@Composable
fun GalleryDetailSetterSheet(
    isShow: Boolean,
    onSetHomeClick: () -> Unit,
    onSetBothClick: () -> Unit,
    onDismissRequest: () -> Unit
) {
    ComposeBottomSheetDialog(
        isShow = isShow,
        onDismissRequest = onDismissRequest,
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary
        ) {
            Column(
                modifier = Modifier
                    .navigationBarsPadding()
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GalleryDetailSetterSheetItem(
                    iconResId = R.drawable.ic_wallpaper_setter_home,
                    labelResId = R.string.wallpaper_setter_home,
                    onClick = onSetHomeClick
                )
                GalleryDetailSetterSheetItem(
                    iconResId = R.drawable.ic_wallpaper_setter_home_lock,
                    labelResId = R.string.wallpaper_setter_home_lock,
                    onClick = onSetBothClick
                )
            }
        }
    }
}

@Composable
private fun GalleryDetailSetterSheetItem(
    @DrawableRes iconResId: Int,
    @StringRes labelResId: Int,
    onClick: () -> Unit
) {
    Surface(color = Color.Transparent) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick.invoke() }
                .padding(
                    horizontal = 16.dp,
                    vertical = 24.dp
                ),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(iconResId),
                contentDescription = DEFAULT_DESC
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(labelResId),
                color = MaterialTheme.colorScheme.onPrimary,
                fontFamily = googleSans
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun GalleryDetailSetterSheetPreview() {
    RelicAppTheme {
        GalleryDetailSetterSheet(
            isShow = true,
            onSetHomeClick = {},
            onSetBothClick = {},
            onDismissRequest = {}
        )
    }
}