package io.dev.relic.feature.main.unit.home.widget.card

import androidx.compose.foundation.layout.Spacer
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
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import io.dev.relic.R
import io.dev.relic.feature.main.unit.home.widget.HomePageCardTitle

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeFoodRecipesCard(
    isLoading: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    HomePageCardTitle(titleResId = R.string.home_card_food_recipes_title)
    Spacer(modifier = modifier.height(8.dp))
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 16.dp)
            .placeholder(
                visible = isLoading,
                highlight = PlaceholderHighlight.shimmer()
            ),
        enabled = !isLoading,
        shape = RoundedCornerShape(16.dp),
        elevation = 2.dp
    ) {
        // TODO
    }
}

@Composable
@Preview(showBackground = true)
private fun HomeFoodRecipesPreview() {
    HomeFoodRecipesCard(
        isLoading = false,
        onClick = {}
    )
}