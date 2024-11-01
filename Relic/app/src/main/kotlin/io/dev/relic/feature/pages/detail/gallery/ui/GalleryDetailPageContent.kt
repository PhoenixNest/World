package io.dev.relic.feature.pages.detail.gallery.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.core.ui.CommonNoDataComponent
import io.core.ui.theme.mainIconColorLight
import io.core.ui.theme.mainThemeColor
import io.data.model.pixabay.PixabayDataModel
import io.dev.relic.feature.function.gallery.widget.OnlineWallpaperCover

@Composable
fun GalleryDetailPageContent(
    model: PixabayDataModel,
    isShowPreview: Boolean,
    onBackClick: () -> Unit,
    onPreviewClick: () -> Unit,
    onSetWallpaperClick: () -> Unit,
    onSetBothClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (model.originalImageUrl.isNullOrEmpty()) {
            CommonNoDataComponent()
            return
        }

        OnlineWallpaperCover(
            url = model.originalImageUrl!!,
            modifier = Modifier.fillMaxSize()
        )
        GalleryDetailPageTopBar(
            isShowPreview = isShowPreview,
            onBackClick = onBackClick,
            onPreviewClick = onPreviewClick,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun GalleryDetailPageTopBar(
    isShowPreview: Boolean,
    onBackClick: () -> Unit,
    onPreviewClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        GalleryDetailBackButton(onBackClick = onBackClick)
        GalleryDetailPreviewButton(
            isShowPreview = isShowPreview,
            onPreviewClick = onPreviewClick,
        )
    }
}

@Composable
private fun GalleryDetailBackButton(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onBackClick,
        modifier = modifier
            .padding(
                horizontal = 16.dp,
                vertical = 32.dp
            )
            .wrapContentSize()
            .background(
                color = mainThemeColor.copy(alpha = 0.3F),
                shape = CircleShape
            )
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
            contentDescription = DEFAULT_DESC,
            tint = mainIconColorLight
        )
    }
}

@Composable
private fun GalleryDetailPreviewButton(
    isShowPreview: Boolean,
    onPreviewClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val previewIconPainter = if (isShowPreview) {
        painterResource(io.module.media.R.drawable.ic_hide_preview)
    } else {
        painterResource(io.module.media.R.drawable.ic_show_preview)
    }

    val previewText = if (isShowPreview) {
        stringResource(io.module.media.R.string.wallpaper_preview_on)
    } else {
        stringResource(io.module.media.R.string.wallpaper_preview_off)
    }

    Surface(
        modifier = modifier.padding(
            horizontal = 16.dp,
            vertical = 32.dp
        ),
        color = Color.Transparent,
        shape = RoundedCornerShape(36.dp)
    ) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .clickable { onPreviewClick.invoke() }
                .padding(12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = previewIconPainter,
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = DEFAULT_DESC
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = previewText,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun GalleryDetailPageContentPreview() {
    GalleryDetailPageContent(
        model = PixabayDataModel(
            id = null,
            previewImageUrl = null,
            previewImageWidth = null,
            previewImageHeight = null,
            webFormatImageUrl = null,
            webFormatImageWidth = null,
            webFormatImageHeight = null,
            originalImageUrl = null,
            originalImageWidth = null,
            originalImageHeight = null,
            author = null,
            authorAvatarUrl = null,
            authorPageUrl = null,
            likes = null
        ),
        isShowPreview = false,
        onBackClick = {},
        onPreviewClick = {},
        onSetWallpaperClick = {},
        onSetBothClick = {}
    )
}