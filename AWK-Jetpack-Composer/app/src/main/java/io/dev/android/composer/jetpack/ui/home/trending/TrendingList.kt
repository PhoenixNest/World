package io.dev.android.composer.jetpack.ui.home.trending

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.dev.android.composer.jetpack.model.home.TrendingModel

@Composable
fun TrendingList(
    listData: List<TrendingModel>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        // contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ) {
        items(listData) { model ->
            TrendingItem(
                imageResId = model.imageResId,
                stringResId = model.stringResId
            )
        }
    }
}