package io.module.map.tomtom

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.tomtom.sdk.map.display.camera.CameraPosition
import io.module.map.tomtom.ui.TomTomMapComponent

typealias TomTomMapFactory = @Composable () -> Unit

@Composable
fun TomTomMapFactory(
    modifier: Modifier = Modifier,
    cameraPosition: CameraPosition,
    onMapLoaded: () -> Unit = {},
    content: @Composable () -> Unit = {}
): TomTomMapFactory {
    return {
        val mapVisible by remember {
            mutableStateOf(true)
        }

        if (mapVisible) {
            TomTomMapComponent(
                onLocationUpdate = {},
                modifier = modifier
            )
        }
    }
}