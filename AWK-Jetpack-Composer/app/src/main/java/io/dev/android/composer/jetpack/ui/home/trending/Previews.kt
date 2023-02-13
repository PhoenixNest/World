package io.dev.android.composer.jetpack.ui.home.trending

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.dev.android.composer.jetpack.R
import io.dev.android.composer.jetpack.model.home.TrendingModel
import io.dev.android.composer.jetpack.ui.theme.AWKJetpackComposerTheme

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