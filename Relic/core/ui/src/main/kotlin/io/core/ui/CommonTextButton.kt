package io.core.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CommonTextButton(
    @StringRes textResId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnable: Boolean = true,
    containerModifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.primary
) {
    TextButton(
        onClick = onClick,
        modifier = containerModifier
            .fillMaxWidth()
            .height(52.dp),
        enabled = isEnable,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.textButtonColors(
            containerColor = backgroundColor,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(
            text = stringResource(id = textResId),
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = modifier.align(alignment = Alignment.CenterVertically),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
@Preview
private fun CommonTextButtonPreview() {
    Box(modifier = Modifier.padding(40.dp)) {
        CommonTextButton(
            textResId = R.string.app_name,
            onClick = {}
        )
    }
}