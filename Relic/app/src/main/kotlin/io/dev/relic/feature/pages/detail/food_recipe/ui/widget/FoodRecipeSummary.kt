package io.dev.relic.feature.pages.detail.food_recipe.ui.widget

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import io.common.util.StringUtil.formatHTML
import io.core.ui.theme.RelicFontFamily.googleSans

@Composable
fun FoodRecipeSummary(
    summary: String,
    modifier: Modifier = Modifier
) {
    val annotatedString = buildAnnotatedString {
        append(formatHTML(summary))
    }
    Text(
        text = annotatedString,
        modifier = modifier,
        color = MaterialTheme.colorScheme.onSurface.copy(0.5F),
        fontFamily = googleSans,
        style = MaterialTheme.typography.bodyMedium
    )
}