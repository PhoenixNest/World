package io.module.map.tomtom

import androidx.compose.runtime.AbstractApplier
import com.tomtom.sdk.map.display.TomTomMap
import com.tomtom.sdk.map.display.ui.MapView

interface TomTomMapNode {
    fun onAttached() {}
    fun onRemoved() {}
    fun onCleared() {}
}

private object TomTomMapNodeRoot : TomTomMapNode

class TomTomMapApplier(
    val map: TomTomMap,
    internal val mapView: MapView
) : AbstractApplier<TomTomMapNode>(TomTomMapNodeRoot) {

    private val decorations = mutableListOf<TomTomMapNode>()

    override fun insertBottomUp(index: Int, instance: TomTomMapNode) {
        decorations.add(index, instance)
        instance.onAttached()
    }

    override fun insertTopDown(index: Int, instance: TomTomMapNode) {
        // insertBottomUp is preferred
    }

    override fun move(from: Int, to: Int, count: Int) {
        decorations.move(
            from = from,
            to = to,
            count = count
        )
    }

    override fun onClear() {
        map.clear()
        decorations.forEach { it.onCleared() }
        decorations.clear()
    }

    override fun remove(index: Int, count: Int) {
        repeat(count) {
            decorations[index + it].onRemoved()
        }
        decorations.remove(index, count)
    }
}