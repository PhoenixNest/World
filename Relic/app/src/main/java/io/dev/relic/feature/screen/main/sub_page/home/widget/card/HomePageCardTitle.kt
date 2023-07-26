package io.dev.relic.feature.screen.main.sub_page.home.widget.card

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.dev.relic.R
import io.dev.relic.ui.theme.RelicFontFamily
import io.dev.relic.ui.theme.mainTextColor

@Composable
fun HomePageCardTitle(
    @StringRes titleResId: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(
            modifier = modifier
                .width(8.dp)
                .height(16.dp)
                .background(
                    color = Color.DarkGray,
                    shape = RoundedCornerShape(8.dp)
                )
        )
        Spacer(modifier = modifier.width(8.dp))
        Text(
            text = stringResource(id = titleResId),
            style = TextStyle(
                color = mainTextColor,
                fontWeight = FontWeight.Bold,
                fontFamily = RelicFontFamily.ubuntu
            )
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun HomePageCardTitlePreview() {
    HomePageCardTitle(titleResId = R.string.app_name)
}