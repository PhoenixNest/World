package io.module.map.kit

import androidx.compose.runtime.Composition
import kotlinx.coroutines.awaitCancellation

internal suspend inline fun disposingComposition(factory: () -> Composition) {
    val composition = factory()
    try {
        awaitCancellation()
    } catch (exception: Exception) {
        exception.printStackTrace()
    } finally {
        composition.dispose()
    }
}