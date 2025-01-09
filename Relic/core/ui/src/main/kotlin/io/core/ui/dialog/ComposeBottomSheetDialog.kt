package io.core.ui.dialog

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

@Composable
fun ComposeBottomSheetDialog(
    isShow: Boolean,
    modifier: Modifier = Modifier,
    isCancelable: Boolean = true,
    isCanceledOnTouchOutside: Boolean = true,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    BackHandler(enabled = isShow) {
        if (isCancelable) {
            onDismissRequest.invoke()
        }
    }

    Box(modifier = modifier) {
        AnimatedVisibility(
            visible = isShow,
            enter = fadeIn(animationSpec = tween(durationMillis = 400, easing = LinearEasing)),
            exit = fadeOut(animationSpec = tween(durationMillis = 400, easing = LinearEasing))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.DarkGray.copy(alpha = 0.3F))
                    .clickableNoRipple {
                        if (isCanceledOnTouchOutside) {
                            onDismissRequest.invoke()
                        }
                    }
            )
        }
        DialogContent(
            isShow = isShow,
            isCancelable = isCancelable,
            onDismissRequest = onDismissRequest,
            content = content,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun DialogContent(
    isShow: Boolean,
    isCancelable: Boolean,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    var offsetY by remember {
        mutableFloatStateOf(0F)
    }

    val offsetYAnimate by animateFloatAsState(
        targetValue = offsetY,
        label = "ComposeBottomSheetDialogLabel"
    )

    var bottomSheetHeight by remember {
        mutableFloatStateOf(0F)
    }

    AnimatedVisibility(
        visible = isShow,
        modifier = modifier
            .onGloballyPositioned {
                bottomSheetHeight = it.size.height.toFloat()
            }
            .offset {
                IntOffset(0, offsetYAnimate.roundToInt())
            }
            .draggable(
                state = rememberDraggableState {
                    offsetY = (offsetY + it.toInt()).coerceAtLeast(0F)
                },
                orientation = Orientation.Vertical,
                onDragStarted = {
                    //
                },
                onDragStopped = {
                    if (isCancelable
                        && (offsetY > (bottomSheetHeight / 2))
                    ) {
                        onDismissRequest.invoke()
                    } else {
                        offsetY = 0F
                    }
                }
            )
            .clickableNoRipple {
                //
            },
        enter = slideInVertically(
            animationSpec = tween(durationMillis = 400, easing = LinearOutSlowInEasing),
            initialOffsetY = {
                2 * it
            }
        ),
        exit = slideOutVertically(
            animationSpec = tween(durationMillis = 400, easing = LinearOutSlowInEasing),
            targetOffsetY = {
                it
            }
        )
    ) {
        DisposableEffect(null) {
            onDispose {
                offsetY = 0F
            }
        }
        Box {
            content.invoke()
        }
    }
}

@Composable
private fun Modifier.clickableNoRipple(onClick: () -> Unit): Modifier {
    return then(
        other = Modifier.clickable(
            onClickLabel = null,
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
            onClick = onClick
        )
    )
}