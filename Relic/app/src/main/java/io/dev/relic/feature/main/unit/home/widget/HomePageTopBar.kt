package io.dev.relic.feature.main.unit.home.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Hexagon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.dev.relic.R
import io.dev.relic.global.RelicConstants.ComposeUi.DEFAULT_DESC
import io.dev.relic.ui.theme.RelicFontFamily
import io.dev.relic.ui.theme.mainTextColor

@Composable
fun HomePageTopBar(
    onOpenDrawer: () -> Unit,
    onNavigateToTodoPage: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(240.dp)
            .background(
                color = Color.DarkGray,
                shape = CutCornerShape(bottomEnd = 72.dp)
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onOpenDrawer) {
                    Icon(
                        imageVector = Icons.Rounded.Hexagon,
                        contentDescription = DEFAULT_DESC,
                        modifier = modifier.size(36.dp),
                        tint = Color.White
                    )
                }
            }
            Spacer(modifier = modifier.height(24.dp))
            Text(
                text = "👋 Hi, Relic",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = RelicFontFamily.fasthand
                )
            )
            Spacer(modifier = modifier.height(12.dp))
            Button(
                onClick = onNavigateToTodoPage,
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
    }
}

@Composable
@Preview(showBackground = true)
private fun HomePageTopBarPreview() {
    HomePageTopBar(
        onOpenDrawer = {},
        onNavigateToTodoPage = {}
    )
}