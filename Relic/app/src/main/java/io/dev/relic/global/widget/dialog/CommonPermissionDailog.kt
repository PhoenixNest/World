package io.dev.relic.global.widget.dialog

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import io.dev.relic.R
import io.dev.relic.global.widget.CommonTextButton
import io.dev.relic.ui.theme.RelicFontFamily
import io.dev.relic.ui.theme.mainTextColor

@Composable
fun CommonPermissionDialog(
    @StringRes titleResId: Int,
    @StringRes descResId: Int,
    onAcceptClick: () -> Unit,
    onDeniedClick: () -> Unit,
    onDismiss: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray.copy(alpha = 0.6F)),
        contentAlignment = Alignment.Center
    ) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        ) {
            Surface(
                modifier = Modifier.width(280.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = titleResId),
                        style = TextStyle(
                            color = mainTextColor,
                            fontSize = 18.sp,
                            fontFamily = RelicFontFamily.ubuntu,
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(id = descResId),
                        style = TextStyle(
                            color = mainTextColor,
                            fontFamily = RelicFontFamily.ubuntu,
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(modifier = Modifier.height(36.dp))
                    CommonTextButton(
                        textResId = R.string.permission_accept,
                        onClick = onAcceptClick
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    CommonTextButton(
                        textResId = R.string.permission_denied,
                        onClick = onDeniedClick,
                        textColor = mainTextColor,
                        backgroundColor = Color.Transparent
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun CommonPermissionDialogPreview() {
    CommonPermissionDialog(
        titleResId = R.string.permission_location,
        descResId = R.string.permission_location_desc,
        onAcceptClick = {},
        onDeniedClick = {},
        onDismiss = {}
    )
}