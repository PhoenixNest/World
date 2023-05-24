package io.dev.relic.feature.main.unit.home

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
fun HomePageRoute(onNavigate: () -> Unit) {
    HomePage(onNavigate = onNavigate)
}

@Composable
private fun HomePage(
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(id = R.string.home_title))
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun HomePagePreview() {
    HomePage(onNavigate = {})
}