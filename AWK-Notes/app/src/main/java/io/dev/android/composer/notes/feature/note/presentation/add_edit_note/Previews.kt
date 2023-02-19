package io.dev.android.composer.notes.feature.note.presentation.add_edit_note

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.dev.android.composer.notes.feature.note.presentation.add_edit_note.components.TransparentHintTextField

@Preview(
    showBackground = true,
    group = "AddEditNoteScreen",
    name = "Hint_Visible"
)
@Composable
fun TransparentHintFieldPreview_Hint_Invisible() {
    TransparentHintTextField(
        text = "",
        hint = "Input something",
        isHintVisible = true,
        onValueChange = {},
        onFocusChange = {}
    )
}

@Preview(
    showBackground = true,
    group = "AddEditNoteScreen",
    name = "Hint_Visible"
)
@Composable
fun TransparentHintFieldPreview_Hint_Visible() {
    TransparentHintTextField(
        text = "Input Data",
        hint = "Input something",
        isHintVisible = false,
        onValueChange = {},
        onFocusChange = {}
    )
}