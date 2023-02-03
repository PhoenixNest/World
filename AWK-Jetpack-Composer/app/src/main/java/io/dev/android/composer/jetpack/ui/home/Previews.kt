package io.dev.android.composer.jetpack.ui.home

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.dev.android.composer.jetpack.R
import io.dev.android.composer.jetpack.model.home.FavouriteModel
import io.dev.android.composer.jetpack.model.home.TrendingModel
import io.dev.android.composer.jetpack.ui.home.favorite.FavoriteGridItem
import io.dev.android.composer.jetpack.ui.home.favorite.FavouriteGridList
import io.dev.android.composer.jetpack.ui.home.trending.TrendingItem
import io.dev.android.composer.jetpack.ui.home.trending.TrendingList
import io.dev.android.composer.jetpack.ui.theme.AWKJetpackComposerTheme

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    SearchBar()
}

@Preview(showBackground = true)
@Composable
fun TrendingItemPreview() {
    AWKJetpackComposerTheme {
        TrendingItem(
            imageResId = R.drawable.ab1_inversions,
            stringResId = R.string.ab1_inversions
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TrendingListPreview() {
    val testListData = TrendingModel.testTrendingList
    TrendingList(listData = testListData)
}

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