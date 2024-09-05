package io.dev.relic.feature.pages.detail.food_recipe.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.core.ui.R

@Composable
fun FoodRecipeLikeButton(
    isLike: Boolean,
    onLikeClick: () -> Unit
) {
    val iconSource = painterResource(
        id = if (isLike) {
            R.drawable.ic_like
        } else {
            R.drawable.ic_dislike
        }
    )

    IconButton(onClick = onLikeClick) {
        Image(
            painter = iconSource,
            contentDescription = DEFAULT_DESC
        )
    }
}

@Composable
@Preview
private fun FoodRecipeLikeButtonPreview() {
    Column {
        FoodRecipeLikeButton(isLike = false, onLikeClick = {})
        FoodRecipeLikeButton(isLike = true, onLikeClick = {})
    }
}