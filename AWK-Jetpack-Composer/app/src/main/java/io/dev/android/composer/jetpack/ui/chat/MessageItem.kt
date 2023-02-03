package io.dev.android.composer.jetpack.ui.chat

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.dev.android.composer.jetpack.R
import io.dev.android.composer.jetpack.model.Message

const val defaultContentDes = "Default Content Description"

@Composable
fun MessageCard(msg: Message) {

    var isExpand by remember {
        mutableStateOf(false)
    }

    val surfaceColor by animateColorAsState(
        targetValue = if (isExpand) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    )

    Row(modifier = Modifier.padding(8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.ic_android),
            contentDescription = defaultContentDes,
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .border(1.2.dp, MaterialTheme.colors.secondary, CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.clickable {
            isExpand = !isExpand
        }) {
            Text(
                text = msg.user.userName,
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.subtitle2
            )

            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = msg.content,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(4.dp),
                    maxLines = if (isExpand) Int.MAX_VALUE else 1,
                )
            }
        }
    }
}