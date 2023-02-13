package io.dev.android.composer.jetpack.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.dev.android.composer.jetpack.R
import io.dev.android.composer.jetpack.model.AnimTestModel
import io.dev.android.composer.jetpack.model.home.FavouriteModel
import io.dev.android.composer.jetpack.model.home.TrendingModel
import io.dev.android.composer.jetpack.ui.home.anim_list.AnimList
import io.dev.android.composer.jetpack.ui.home.favorite.FavouriteGridList
import io.dev.android.composer.jetpack.ui.home.trending.TrendingList

@Composable
fun HomePage(
    trendingListData: List<TrendingModel>,
    favouriteListData: List<FavouriteModel>,
    animListData: List<AnimTestModel>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(vertical = 8.dp)
    ) {
        SearchBar(Modifier.padding(horizontal = 8.dp))

        HomeSectionPanel(titleResId = R.string.align_your_body) {
            TrendingList(listData = trendingListData)
        }

        HomeSectionPanel(titleResId = R.string.favorite_collections) {
            FavouriteGridList(listData = favouriteListData)
        }

        HomeSectionPanel(titleResId = R.string.test_list_title) {
            AnimList(listData = animListData)
        }
    }
}