package io.dev.android.composer.jetpack.ui.home.anim_list

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.dev.android.composer.jetpack.model.AnimTestModel

@Composable
fun AnimList(
    listData: List<AnimTestModel>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(vertical = 8.dp),
    ) {
        items(listData) { model ->
            AnimListItem(model)
        }
    }
}