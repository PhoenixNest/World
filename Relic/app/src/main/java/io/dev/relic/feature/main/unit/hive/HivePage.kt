package io.dev.relic.feature.main.unit.hive

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.dev.relic.R

@Composable
fun HivePageRoute(onNavigate: () -> Unit) {
    HivePage(onNavigate = onNavigate)
}

@Composable
private fun HivePage(
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(id = R.string.hive_title))
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun HivePagePreview() {
    HivePage(onNavigate = {})
}