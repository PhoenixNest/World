package io.dev.relic.feature.main.unit.todo

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
fun TodoPageRoute(onNavigate: () -> Unit) {
    TodoPage(onNavigate = onNavigate)
}

@Composable
private fun TodoPage(
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(id = R.string.todo_title))
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun TodoPagePreview() {
    TodoPage(onNavigate = {})
}