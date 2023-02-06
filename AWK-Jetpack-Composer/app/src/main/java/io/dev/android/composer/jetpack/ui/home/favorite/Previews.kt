package io.dev.android.composer.jetpack.ui.home.favorite

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.dev.android.composer.jetpack.R
import io.dev.android.composer.jetpack.model.home.FavouriteModel

@Preview(
    showBackground = true,
    backgroundColor = 0xFFF0EAE2
)
@Composable
fun FavoriteItemPreview() {
    FavoriteGridItem(
        imageResId = R.drawable.fc1_short_mantras,
        stringResId = R.string.fc1_short_mantras,
        modifier = Modifier.height(72.dp)
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFF0EAE2
)
@Composable
fun FavouriteGridListPreView() {
    FavouriteGridList(FavouriteModel.testFavouriteList)
}