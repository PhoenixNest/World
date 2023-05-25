package io.dev.relic.feature.main.unit.home.widget.card

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeFoodRecipesCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 2.dp
    ) {
        // TODO
    }
}

@Composable
@Preview(showBackground = true)
private fun HomeFoodRecipesPreview() {
    HomeFoodRecipesCard(onClick = {})
}