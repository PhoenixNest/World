package io.dev.relic.global.widget

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.dev.relic.R
import io.dev.relic.ui.theme.RelicFontFamily
import io.dev.relic.ui.theme.mainButtonColor

@Composable
fun CommonTextButton(
    @StringRes textResId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnable: Boolean = true,
    containerModifier: Modifier = Modifier,
    textColor: Color = Color.White,
    fontSize: TextUnit = 14.sp,
    backgroundColor: Color = mainButtonColor
) {
    TextButton(
        onClick = onClick,
        modifier = containerModifier
            .fillMaxWidth()
            .height(52.dp),
        enabled = isEnable,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = backgroundColor,
            contentColor = Color.LightGray
        )
    ) {
        Text(
            text = stringResource(id = textResId),
            modifier = modifier.align(alignment = Alignment.CenterVertically),
            style = TextStyle(
                color = textColor,
                fontSize = fontSize,
                fontFamily = RelicFontFamily.ubuntu,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
@Preview
private fun CommonTextButtonPreview() {
    Box(modifier = Modifier.padding(40.dp)) {
        CommonTextButton(
            textResId = R.string.app_name,
            onClick = {}
        )
    }
}