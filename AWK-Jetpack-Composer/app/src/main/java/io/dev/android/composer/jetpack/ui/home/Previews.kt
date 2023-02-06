package io.dev.android.composer.jetpack.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.dev.android.composer.jetpack.R
import io.dev.android.composer.jetpack.model.home.FavouriteModel
import io.dev.android.composer.jetpack.model.home.TrendingModel
import io.dev.android.composer.jetpack.ui.home.trending.TrendingList
import io.dev.android.composer.jetpack.ui.theme.AWKJetpackComposerTheme

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    SearchBar()
}

@Preview(showBackground = true)
@Composable
fun HomeSectionPreview() {
    HomeSectionPanel(titleResId = R.string.app_name) {
        TrendingList(listData = TrendingModel.testTrendingList)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBottomNavigationPreview() {
    MyBottomNavigationBar()
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFF0EAE2,
    heightDp = 180
)
@Composable
fun HomePagePreview() {
    AWKJetpackComposerTheme {
        HomePage(
            trendingListData = TrendingModel.testTrendingList,
            favouriteListData = FavouriteModel.testFavouriteList
        )
    }
}
