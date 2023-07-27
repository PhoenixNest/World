package io.dev.relic.feature.screen.mine

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MineScreenRoute(onBackClick: () -> Unit) {
    MineScreen(onBackClick = onBackClick)
}

@Composable
private fun MineScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {

}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun MineScreenPreview() {
    MineScreen(onBackClick = {})
}
