package io.dev.relic.feature.screen.main.sub_page.hive.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import io.dev.relic.global.RelicConstants.ComposeUi.DEFAULT_DESC

@Composable
fun HivePageUserPanel(
    onNavigateToMine: () -> Unit,
    modifier: Modifier = Modifier,
    containerModifier: Modifier = Modifier
) {
    Box(
        modifier = containerModifier
            .fillMaxWidth()
            .height(200.dp)
            .background(color = Color.DarkGray)
            .statusBarsPadding(),
        contentAlignment = Alignment.BottomStart
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = modifier.size(64.dp),
                    color = Color.LightGray,
                    shape = CircleShape
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("")
                            .crossfade(true)
                            .build(),
                        contentDescription = DEFAULT_DESC,
                        modifier = modifier
                            .fillMaxSize()
                            .clickable {
                                onNavigateToMine.invoke()
                            }
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun HivePageTopBarPreview() {
    HivePageUserPanel(onNavigateToMine = {})
}