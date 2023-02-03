package io.dev.android.composer.jetpack.ui.home.trending

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.dev.android.composer.jetpack.ui.data.defaultContentDes

@Composable
fun TrendingItem(
    @DrawableRes imageResId: Int,
    @StringRes stringResId: Int,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = defaultContentDes,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
        )

        Text(
            text = stringResource(id = stringResId),
            style = TextStyle(fontSize = 14.sp),
            modifier = Modifier.padding(
                top = 16.dp,
                bottom = 8.dp
            )
        )
    }
}