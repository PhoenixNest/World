package io.dev.relic.feature.pages.detail.gallery.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.core.ui.dialog.ComposeBottomSheetDialog
import io.core.ui.theme.RelicFontFamily.googleSans
import io.dev.relic.R

@Composable
fun GalleryDetailSuccessSheet(
    isShow: Boolean,
    onGo2DesktopClick: () -> Unit,
    onDismissRequest: () -> Unit
) {
    ComposeBottomSheetDialog(
        isShow = isShow,
        onDismissRequest = onDismissRequest
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary
        ) {
            Column(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 24.dp
                    )
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GalleryDetailSuccessDesc()
                Spacer(modifier = Modifier.height(32.dp))
                GalleryDetailSuccessButton(onClick = onGo2DesktopClick)
            }
        }
    }
}

@Composable
private fun GalleryDetailSuccessDesc() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_checked),
            contentDescription = DEFAULT_DESC,
            modifier = Modifier.size(36.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(io.module.media.R.string.wallpaper_setter_succeed),
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 16.sp,
            fontFamily = googleSans,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun GalleryDetailSuccessButton(onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .width(160.dp)
            .height(48.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
        shape = CircleShape
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClick.invoke() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(io.module.media.R.string.wallpaper_go_to_desktop),
                fontFamily = googleSans
            )
        }
    }
}

@Composable
@Preview
private fun GalleryDetailSuccessSheetPreview() {
    GalleryDetailSuccessSheet(
        isShow = true,
        onGo2DesktopClick = {},
        onDismissRequest = {}
    )
}