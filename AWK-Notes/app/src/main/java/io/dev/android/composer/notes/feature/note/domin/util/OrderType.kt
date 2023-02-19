package io.dev.android.composer.notes.feature.note.domin.util

sealed class OrderType {

    object Ascending : OrderType()

    object Descending : OrderType()

}
