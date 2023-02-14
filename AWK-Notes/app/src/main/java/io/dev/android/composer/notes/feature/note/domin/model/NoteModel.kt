package io.dev.android.composer.notes.feature.note.domin.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.dev.android.composer.notes.ui.theme.*

@Entity
data class NoteModel(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
) {
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidNoteException(message: String) : Exception(message)
