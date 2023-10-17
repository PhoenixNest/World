package io.dev.relic.domain.location.map.amap

import com.amap.api.maps.AMapOptions
import com.amap.api.maps.model.MyLocationStyle

/**
 * @see AMapPreferenceKeys
 * */
object AMapConfig {

    private const val TAG = "AMapConfig"

    /**
     * [3D地图 • MyLocationStyle](https://lbs.amap.com/api/android-sdk/guide/create-map/mylocation)
     *
     * @see MyLocationStyle
     * */
    object MapStyle {

        private const val TAG = "${AMapConfig.TAG}_MyLocationStyle"

        fun defaultStyle(): MyLocationStyle {
            return MyLocationStyle().apply {
                showMyLocation(true)
                myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE)
            }
        }
    }

    /**
     * [3D地图 • AMapOptions](https://a.amap.com/lbs/static/unzip/Android_Map_Doc/index.html)
     *
     * @see AMapOptions
     * */
    object OptionsConfig {

        private const val TAG = "${AMapConfig.TAG}_AMapOptions"

        fun defaultConfig(): AMapOptions {
            return AMapOptions().apply {
                compassEnabled(true)
                rotateGesturesEnabled(true)
                scaleControlsEnabled(true)
                scrollGesturesEnabled(true)
                tiltGesturesEnabled(true)
                zoomControlsEnabled(true)
                zoomGesturesEnabled(true)
            }
        }
    }
}