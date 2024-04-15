package io.dev.relic.feature.function.todo.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.core.ui.CommonHorizontalIconTextButton
import io.core.ui.theme.RelicFontFamily.ubuntu
import io.core.ui.theme.mainTextColor
import io.core.ui.theme.mainTextColor80
import io.core.ui.theme.mainThemeColor
import io.core.ui.theme.mainThemeColorAccent
import io.dev.relic.R

@Composable
fun TodoNoDataComponent(onCreateClick: () -> Unit) {

    val lottieRes by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.lottie_rocket)
    )

    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.todo_intro_title),
            style = TextStyle(
                color = mainTextColor,
                fontSize = 24.sp,
                fontFamily = ubuntu,
                fontWeight = FontWeight.Bold,
                lineHeight = TextUnit(
                    value = 1.6F,
                    type = TextUnitType.Em
                )
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color.Transparent
        ) {
            TodoNoDataComponentCover()
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
            ) {
                TodoNoDataComponentIntro(
                    modifier = Modifier.weight(2F),
                    onCreateClick = onCreateClick
                )
                LottieAnimation(
                    composition = lottieRes,
                    modifier = Modifier
                        .weight(1F)
                        .fillMaxHeight(),
                    restartOnPlay = true,
                    iterations = Int.MAX_VALUE,
                    alignment = Alignment.Center,
                    contentScale = ContentScale.FillHeight
                )
            }
        }
    }
}

@Composable
private fun TodoNoDataComponentCover() {
    Image(
        painter = painterResource(id = R.mipmap.astrology),
        contentDescription = DEFAULT_DESC,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = mainThemeColor.copy(alpha = 0.3F))
    )
}

@Composable
private fun TodoNoDataComponentIntro(
    modifier: Modifier = Modifier,
    onCreateClick: () -> Unit
) {

    val titleTextStyle = TextStyle(
        color = mainTextColor,
        fontSize = 18.sp,
        fontFamily = ubuntu,
        fontWeight = FontWeight.Bold,
        lineHeight = TextUnit(
            value = 1.6F,
            type = TextUnitType.Em
        )
    )

    val listTextStyle = TextStyle(
        color = mainTextColor80,
        fontSize = 13.sp,
        fontFamily = ubuntu,
        fontWeight = FontWeight.Bold
    )

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.Top
        ),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = stringResource(R.string.todo_intro_goal_title), style = titleTextStyle)
        Text(text = stringResource(R.string.todo_intro_goal_1), style = listTextStyle)
        Text(text = stringResource(R.string.todo_intro_goal_2), style = listTextStyle)
        Text(text = stringResource(R.string.todo_intro_goal_3), style = listTextStyle)
        Text(text = stringResource(R.string.todo_intro_goal_more), style = listTextStyle)
        CommonHorizontalIconTextButton(
            iconResId = R.drawable.ic_add,
            labelResId = R.string.todo_intro_create,
            onClick = onCreateClick,
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = mainThemeColorAccent,
            shape = RoundedCornerShape(12.dp)
        )
    }
}

@Composable
@Preview
private fun TodoNoDataComponentPreview() {
    TodoNoDataComponent(onCreateClick = {})
}