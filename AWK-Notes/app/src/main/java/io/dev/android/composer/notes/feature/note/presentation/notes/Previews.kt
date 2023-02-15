package io.dev.android.composer.notes.feature.note.presentation.notes

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.dev.android.composer.notes.feature.note.domin.model.NoteModel
import io.dev.android.composer.notes.feature.note.presentation.notes.components.DefaultRadioButton
import io.dev.android.composer.notes.feature.note.presentation.notes.components.NoteItem
import io.dev.android.composer.notes.feature.note.presentation.notes.components.OrderSection
import java.util.*

@Preview(showBackground = true, group = "NotesScreen")
@Composable
fun DefaultRadioButtonPreview() {
    DefaultRadioButton(
        text = "RadioButton",
        selected = false,
        onSelect = { }
    )
}

@Preview(showBackground = true, group = "NotesScreen")
@Composable
fun NoteItemPreview() {
    val testModel = NoteModel(
        id = 0,
        title = "Title",
        content = "Content",
        timestamp = Calendar.getInstance().timeInMillis,
        color = (0xffffab91).toInt(),
    )

    NoteItem(
        noteModel = testModel,
        onDeleteClick = {}
    )
}

@Preview(showBackground = true, group = "NotesScreen")
@Composable
fun OrderSectionPreview() {
    OrderSection(onOrderChange = {})
}