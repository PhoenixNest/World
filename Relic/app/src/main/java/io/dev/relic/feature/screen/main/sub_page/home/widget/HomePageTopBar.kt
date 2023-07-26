package io.dev.relic.feature.screen.main.sub_page.home.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
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
import io.dev.relic.global.widget.CommonIconTextButton
import io.dev.relic.ui.theme.RelicFontFamily

@Composable
fun HomePageTopBar(
    onNavigateToSubscribePage: () -> Unit,
    onNavigateToSettingPage: () -> Unit,
    onNavigateToCreateTodoPage: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(320.dp)
            .background(color = Color.DarkGray)
            .statusBarsPadding(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HomePageTopBarLeftPanel(onCreateTodoClick = onNavigateToCreateTodoPage)
        HomePageTopBarRightPanel(
            onSubscribeClick = onNavigateToSubscribePage,
            onSettingClick = onNavigateToSettingPage
        )
    }
}

@Composable
private fun RowScope.HomePageTopBarLeftPanel(
    onCreateTodoClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .weight(1F)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.TopStart),
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
                text = stringResource(id = R.string.app_name),
                style = TextStyle(
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = RelicFontFamily.fasthand
                )
            )
        }
        CommonIconTextButton(
            icon = Icons.Rounded.Add,
            labelResId = R.string.todo_ext_fab_label,
            onClick = onCreateTodoClick,
            containerModifier = modifier.align(Alignment.BottomStart),
            backgroundColor = Color.White
        )
    }
}

@Composable
private fun RowScope.HomePageTopBarRightPanel(
    onSubscribeClick: () -> Unit,
    onSettingClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val composition: LottieComposition? by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.lottie_home_rocket)
    )

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
            IconButton(onClick = onSubscribeClick) {
                Icon(
                    imageVector = Icons.Rounded.Diamond,
                    contentDescription = DEFAULT_DESC,
                    modifier = modifier.size(24.dp),
                    tint = Color.White
                )
            }
            IconButton(onClick = onSettingClick) {
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
                .wrapContentWidth()
                .height(260.dp)
                .align(Alignment.BottomCenter),
            restartOnPlay = true,
            iterations = Int.MAX_VALUE,
            contentScale = ContentScale.Crop
        )
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