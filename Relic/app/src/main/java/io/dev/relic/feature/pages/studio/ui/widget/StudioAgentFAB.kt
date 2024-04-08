package io.dev.relic.feature.pages.studio.ui.widget

import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.core.ui.theme.RelicFontFamily.ubuntu
import io.core.ui.theme.mainIconColorLight
import io.core.ui.theme.mainTextColor
import io.core.ui.theme.mainThemeColorAccent
import io.dev.relic.R

@Composable
fun StudioAgentFAB(onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        text = {
            Text(
                text = "CHAT WITH GEMINI",
                style = TextStyle(
                    color = mainTextColor,
                    fontFamily = ubuntu,
                    fontWeight = FontWeight.Bold
                )
            )
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_agent),
                contentDescription = DEFAULT_DESC,
                tint = mainIconColorLight
            )
        },
        onClick = onClick,
        backgroundColor = mainThemeColorAccent
    )
}

@Composable
@Preview
private fun StudioAgentFABPreview() {
    StudioAgentFAB(onClick = {})
}