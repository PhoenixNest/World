package io.module.media.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import io.module.media.image.model.ImageInfoModel
import io.module.media.model.MediaBaseInfoModel

private const val ITEMS_PER_ROW = 3

/* ======================== Image Picker ======================== */

@Composable
fun ImagePicker(infoModelList: List<MediaBaseInfoModel>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(ITEMS_PER_ROW),
        modifier = Modifier.fillMaxSize(),
        state = rememberLazyGridState(),
        contentPadding = PaddingValues(4.dp),
        verticalArrangement = Arrangement.spacedBy(
            space = 4.dp,
            alignment = Alignment.Top
        ),
        horizontalArrangement = Arrangement.spacedBy(
            space = 4.dp,
            alignment = Alignment.Start
        )
    ) {
        items(infoModelList) { infoModel ->
            val imageModel = (infoModel as? ImageInfoModel) ?: return@items
            ImagePickerItem(imageModel)
        }
    }
}

@Composable
private fun ImagePickerItem(infoModel: ImageInfoModel) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val widthPerItem = ((screenWidthDp.dp - (4 * 2).dp) / ITEMS_PER_ROW)
    with(infoModel) {
        //
    }
}

/* ======================== Video Picker ======================== */

@Composable
fun VideoPicker(infoModelList: List<MediaBaseInfoModel>) {
    //
}

/* ======================== Audio Picker ======================== */

@Composable
fun AudioPicker(infoModelList: List<MediaBaseInfoModel>) {
    //
}