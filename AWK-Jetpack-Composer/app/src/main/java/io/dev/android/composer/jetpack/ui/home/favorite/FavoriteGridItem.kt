package io.dev.android.composer.jetpack.ui.home.favorite

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.dev.android.composer.jetpack.ui.data.defaultContentDes

@Composable
fun FavoriteGridItem(
    @DrawableRes imageResId: Int,
    @StringRes stringResId: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = /* MaterialTheme.shapes.medium */ RoundedCornerShape(8.dp),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.width(192.dp)
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = defaultContentDes,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(72.dp)
            )

            Text(
                text = stringResource(id = stringResId),
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}