package io.module.media.ui.screen

import androidx.compose.runtime.Composable
import io.module.media.MediaType
import io.module.media.MediaType.AUDIO
import io.module.media.MediaType.IMAGE
import io.module.media.MediaType.VIDEO
import io.module.media.model.MediaBaseInfoModel
import io.module.media.ui.widget.AudioPicker
import io.module.media.ui.widget.ImagePicker
import io.module.media.ui.widget.VideoPicker

@Composable
fun MediaScreen(
    type: MediaType,
    infoModelList: List<MediaBaseInfoModel>
) {
    when (type) {
        IMAGE -> ImagePicker(infoModelList)
        VIDEO -> VideoPicker(infoModelList)
        AUDIO -> AudioPicker(infoModelList)
    }
}