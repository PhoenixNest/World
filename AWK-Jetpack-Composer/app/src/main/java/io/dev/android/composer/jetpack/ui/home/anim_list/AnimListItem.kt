package io.dev.android.composer.jetpack.ui.home.anim_list

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.dev.android.composer.jetpack.model.AnimTestModel

@Composable
fun AnimListItem(
    model: AnimTestModel,
    modifier: Modifier = Modifier,
    itemOnClick: () -> Unit = {}
) {
    val isExpand = remember {
        mutableStateOf(false)
    }

    val extraPadding by animateDpAsState(
        targetValue = if (isExpand.value) 48.dp else 16.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Surface(modifier = modifier.padding(8.dp)) {
        Card(
            elevation = 2.dp,
            backgroundColor = Color(0xFF1D4B71),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = extraPadding.coerceAtLeast(0.dp)
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Welcome, ${model.title}",
                    style = MaterialTheme.typography.body1.copy(
                        color = Color.White
                    )
                )

                ElevatedButton(
                    onClick = /* itemOnClick */ {
                        isExpand.value = !isExpand.value
                    },
                    modifier = Modifier.padding(8.dp),
                ) {
                    Text(
                        text = if (!isExpand.value) model.subTitle else "Show less",
                        style = MaterialTheme.typography.subtitle1,
                    )
                }
            }
        }
    }
}