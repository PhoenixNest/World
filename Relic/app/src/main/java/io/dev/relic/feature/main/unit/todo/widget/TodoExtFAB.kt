package io.dev.relic.feature.main.unit.todo.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.dev.relic.R
import io.dev.relic.global.RelicConstants.ComposeUi.DEFAULT_DESC
import io.dev.relic.ui.theme.RelicFontFamily

@Composable
fun TodoExtFAB(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ExtendedFloatingActionButton(
        text = {
            Text(
                text = stringResource(id = R.string.todo_ext_fab_label),
                style = TextStyle(
                    color = Color.White,
                    fontFamily = RelicFontFamily.ubuntu
                )
            )
        },
        onClick = onClick,
        modifier = modifier,
        icon = {
            Icon(
                imageVector = Icons.Rounded.EditNote,
                contentDescription = DEFAULT_DESC,
                tint = Color.White
            )
        },
        backgroundColor = Color.DarkGray
    )

}

@Composable
@Preview(showBackground = true)
private fun HomePageFABPreview() {
    Box(modifier = Modifier.padding(24.dp)) {
        TodoExtFAB(onClick = {})
    }
}