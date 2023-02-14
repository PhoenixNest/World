package io.dev.android.composer.notes.feature.note.domin.user_case

import io.dev.android.composer.notes.feature.note.domin.model.NoteModel
import io.dev.android.composer.notes.feature.note.domin.repository.NoteRepository
import io.dev.android.composer.notes.feature.note.domin.util.NoteOrder
import io.dev.android.composer.notes.feature.note.domin.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes(
    private val repository: NoteRepository
) {
    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<List<NoteModel>> {
        return repository.getNotes().map { noteModelList ->
            when (noteOrder.orderType) {
                is OrderType.Ascending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> noteModelList.sortedBy { it.title.lowercase() }
                        is NoteOrder.Date -> noteModelList.sortedBy { it.timestamp }
                        is NoteOrder.Color -> noteModelList.sortedBy { it.color }
                    }
                }

                is OrderType.Descending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> noteModelList.sortedByDescending { it.title.lowercase() }
                        is NoteOrder.Date -> noteModelList.sortedByDescending { it.timestamp }
                        is NoteOrder.Color -> noteModelList.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}