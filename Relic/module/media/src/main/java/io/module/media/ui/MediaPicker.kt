package io.module.media.ui

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import io.module.media.MediaType
import io.module.media.MediaType.AUDIO
import io.module.media.MediaType.IMAGE
import io.module.media.MediaType.VIDEO
import io.module.media.image.ImageUtil
import io.module.media.image.model.ImageInfoModel

private const val ITEMS_PER_ROW = 3

@Composable
fun MediaPicker(
    context: Context,
    type: MediaType
) {
    when (type) {
        IMAGE -> ImagePicker(context)
        VIDEO -> VideoPicker(context)
        AUDIO -> AudioPicker(context)
    }
}

/* ======================== Image Picker ======================== */

@Composable
private fun ImagePickerItem(infoModel: ImageInfoModel) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val widthPerItem = ((screenWidthDp.dp - (4 * 2).dp) / ITEMS_PER_ROW)
    with(infoModel) {
        //
    }
}

@Composable
private fun ImagePicker(context: Context) {

    var localImageList = emptyList<ImageInfoModel>()

    LaunchedEffect(Unit) {
        localImageList = ImageUtil.queryLocalPhoto(context)
    }

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
        items(localImageList) { infoModel ->
            ImagePickerItem(infoModel)
        }
    }
}

/* ======================== Video Picker ======================== */

@Composable
private fun VideoPicker(context: Context) {

}

/* ======================== Audio Picker ======================== */

@Composable
private fun AudioPicker(context: Context) {

}