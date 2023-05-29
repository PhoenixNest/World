package io.dev.relic.feature.main.unit.home.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Diamond
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Token
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import io.dev.relic.R
import io.dev.relic.global.RelicConstants.ComposeUi.DEFAULT_DESC
import io.dev.relic.ui.theme.RelicFontFamily
import io.dev.relic.ui.theme.mainTextColor

@Composable
fun HomePageTopBar(
    onNavigateToSubscribePage: () -> Unit,
    onNavigateToSettingPage: () -> Unit,
    onNavigateToCreateTodoPage: () -> Unit,
    modifier: Modifier = Modifier
) {

    val composition: LottieComposition? by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.lottie_home_rocket)
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(
                brush = Brush.radialGradient(
                    listOf(
                        Color(color = 0xFFEF3152),
                        Color.DarkGray
                    ),
                    center = Offset.Zero
                ),
                shape = RoundedCornerShape(
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                )
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .weight(1F)
                .padding(16.dp)
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.Token,
                    contentDescription = DEFAULT_DESC,
                    modifier = modifier.size(36.dp),
                    tint = Color.White
                )
                Spacer(modifier = modifier.width(16.dp))
                Text(
                    text = "Relic",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = RelicFontFamily.fasthand
                    )
                )
            }

            Button(
                onClick = onNavigateToCreateTodoPage,
                modifier = modifier.align(Alignment.BottomStart),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White
                )
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = DEFAULT_DESC,
                        tint = Color.DarkGray
                    )
                    Spacer(modifier = modifier.width(8.dp))
                    Text(
                        text = stringResource(id = R.string.todo_ext_fab_label),
                        style = TextStyle(
                            color = mainTextColor,
                            fontWeight = FontWeight.Bold,
                            fontFamily = RelicFontFamily.ubuntu
                        )
                    )
                }
            }

        }
        Box(
            modifier = modifier
                .fillMaxSize()
                .weight(1F)
        ) {
            Row(
                modifier = modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .background(
                        color = Color.LightGray.copy(alpha = 0.1F),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(
                        horizontal = 8.dp,
                        vertical = 4.dp
                    ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        // TODO
                        onNavigateToSubscribePage.invoke()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Diamond,
                        contentDescription = DEFAULT_DESC,
                        modifier = modifier.size(24.dp),
                        tint = Color.White
                    )
                }
                IconButton(
                    onClick = {
                        // TODO
                        onNavigateToSettingPage.invoke()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Settings,
                        contentDescription = DEFAULT_DESC,
                        modifier = modifier.size(24.dp),
                        tint = Color.White
                    )
                }
            }
            LottieAnimation(
                composition = composition,
                modifier = modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .align(Alignment.BottomCenter),
                restartOnPlay = true,
                iterations = Int.MAX_VALUE,
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
@Preview
private fun HomePageTopBarPreview() {
    HomePageTopBar(
        onNavigateToSubscribePage = {},
        onNavigateToSettingPage = {},
        onNavigateToCreateTodoPage = {}
    )
}