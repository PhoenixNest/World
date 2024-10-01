package io.module.media.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import io.module.media.R

@Composable
fun MediaDeniedScreen(
    onBackClick: () -> Unit,
    onRetryClick: () -> Unit
) {
    Surface(color = MaterialTheme.colorScheme.surface) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 8.dp,
                    end = 8.dp,
                    top = 32.dp
                )
        ) {
            MediaDeniedBackBtn(
                onClick = onBackClick,
                modifier = Modifier.align(Alignment.TopStart)
            )
            MediaDeniedDesc(
                onClick = onRetryClick,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun MediaDeniedBackBtn(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Rounded.Close,
            contentDescription = null
        )
    }
}

@Composable
private fun MediaDeniedDesc(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val appName = context.getString(R.string.app_name)
    val lottieRes by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.lottie_no_permission)
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = lottieRes,
            modifier = Modifier
                .width(240.dp)
                .height(180.dp),
            restartOnPlay = true,
            iterations = Int.MAX_VALUE,
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = stringResource(R.string.no_permission_title),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = context.getString(R.string.no_permission_desc_image_video, appName),
            modifier = Modifier.width(240.dp),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(32.dp))
        MediaDeniedRetryButton(onRetryClick = onClick)
    }
}

@Composable
private fun MediaDeniedRetryButton(onRetryClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .width(240.dp)
            .height(52.dp),
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.primary
    ) {
        Row(
            modifier = Modifier.clickable {
                onRetryClick.invoke()
            },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.Refresh,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(R.string.no_permission_button_label),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun MediaDeniedScreenPreview() {
    MediaDeniedScreen(
        onBackClick = {},
        onRetryClick = {}
    )
}