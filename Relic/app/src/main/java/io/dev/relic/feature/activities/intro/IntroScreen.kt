package io.dev.relic.feature.activities.intro

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.dev.relic.R
import io.dev.relic.global.RelicConstants
import io.dev.relic.global.widget.CommonRoundIcon
import io.dev.relic.global.widget.CommonTextButton
import io.dev.relic.global.widget.dialog.CommonItemDivider
import io.dev.relic.ui.theme.RelicFontFamily
import io.dev.relic.ui.theme.mainTextColor
import io.dev.relic.ui.theme.mainTextColorDark
import io.dev.relic.ui.theme.mainThemeColorAccent

@Composable
fun IntroScreen(onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.mipmap.intro),
            contentDescription = RelicConstants.ComposeUi.DEFAULT_DESC,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        IntroPanel(onClick = onClick)
    }
}

@Composable
private fun BoxScope.IntroPanel(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.Start
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = mainThemeColorAccent.copy(alpha = 0.9F),
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp
                    )
                )
                .navigationBarsPadding()
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                Box(
                    Modifier
                        .height(4.dp)
                        .width(96.dp)
                        .align(Alignment.Center)
                        .background(color = mainTextColor, shape = RoundedCornerShape(12.dp))
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.intro_title),
                    style = TextStyle(
                        color = mainTextColor,
                        fontSize = 52.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = RelicFontFamily.ubuntu
                    )
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(R.string.intro_sub_title),
                modifier = Modifier.padding(horizontal = 24.dp),
                style = TextStyle(
                    color = mainTextColor,
                    fontSize = 20.sp,
                    fontFamily = RelicFontFamily.ubuntu
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            IntroFeaturePanel(onClick = onClick)
        }
    }
}

@Composable
private fun IntroFeaturePanel(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White.copy(alpha = 0.36F),
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp
                )
            )
            .padding(all = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        IntroFeatureItem(
            iconResId = R.drawable.ic_route,
            textResId = R.string.intro_feature_route
        )
        CommonItemDivider()
        IntroFeatureItem(
            iconResId = R.drawable.ic_coffee,
            textResId = R.string.intro_feature_coffee
        )
        CommonItemDivider()
        IntroFeatureItem(
            iconResId = R.drawable.ic_weather,
            textResId = R.string.intro_feature_weather
        )
        Spacer(modifier = Modifier.height(32.dp))
        CommonTextButton(
            textResId = R.string.intro_dive_in,
            onClick = onClick
        )
    }
}

@Composable
private fun IntroFeatureItem(
    @DrawableRes iconResId: Int,
    @StringRes textResId: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CommonRoundIcon(iconResId)
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            stringResource(textResId),
            style = TextStyle(
                color = mainTextColorDark,
                fontSize = 16.sp,
                fontFamily = RelicFontFamily.ubuntu
            )
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun IntroScreenPreview() {
    IntroScreen(onClick = {})
}