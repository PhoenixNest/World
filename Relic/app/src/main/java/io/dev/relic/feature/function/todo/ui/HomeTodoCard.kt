package io.dev.relic.feature.function.todo.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeTodoCard() {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.LightGray
    ) {
        //
    }
}

@Composable
@Preview
private fun HomeTodoCardPreview() {
    HomeTodoCard()
}