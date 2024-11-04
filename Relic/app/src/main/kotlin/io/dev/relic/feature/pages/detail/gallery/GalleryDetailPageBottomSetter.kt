package io.dev.relic.feature.pages.detail.gallery

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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.core.ui.theme.mainThemeColor

@Composable
fun GalleryDetailPageBottomSetter(
    onSetWallpaperClick: () -> Unit,
    onSetBothClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .navigationBarsPadding()
            .fillMaxWidth()
            .wrapContentSize()
            .padding(horizontal = 12.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GallerySetterItem(
            labelResId = io.module.media.R.string.wallpaper_setter_home,
            iconResId = io.module.media.R.drawable.ic_wallpaper_setter_home,
            onClick = onSetWallpaperClick
        )
        GallerySetterItem(
            labelResId = io.module.media.R.string.wallpaper_setter_home_lock,
            iconResId = io.module.media.R.drawable.ic_wallpaper_setter_home_lock,
            onClick = onSetBothClick
        )
    }
}

@Composable
private fun GallerySetterItem(
    @StringRes labelResId: Int,
    @DrawableRes iconResId: Int,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = mainThemeColor.copy(alpha = 0.3F),
        shape = RoundedCornerShape(32.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick.invoke() }
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(iconResId),
                contentDescription = DEFAULT_DESC
            )
            Spacer(Modifier.width(4.dp))
            Text(text = stringResource(labelResId))
        }
    }
}

@Composable
@Preview
private fun GalleryDetailPageBottomSetterPreview() {
    GalleryDetailPageBottomSetter(
        onSetWallpaperClick = {},
        onSetBothClick = {}
    )
}