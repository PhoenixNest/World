package io.dev.relic.domain.location.amap

import com.amap.api.maps.AMapOptions
import com.amap.api.maps.model.MyLocationStyle
import io.dev.relic.core.data.datastore.RelicDatastoreCenter.readSyncData
import io.dev.relic.core.data.datastore.preference_keys.AMapPreferenceKeys
import io.dev.relic.core.data.datastore.preference_keys.AMapPreferenceKeys.AMapOptions.KEY_IS_ENABLE_COMPRESS
import io.dev.relic.core.data.datastore.preference_keys.AMapPreferenceKeys.AMapOptions.KEY_IS_ENABLE_ROTATE_GESTURE
import io.dev.relic.core.data.datastore.preference_keys.AMapPreferenceKeys.AMapOptions.KEY_IS_ENABLE_SCALE_CONTROL_ENABLE
import io.dev.relic.core.data.datastore.preference_keys.AMapPreferenceKeys.AMapOptions.KEY_IS_ENABLE_SCROLL_GESTURE
import io.dev.relic.core.data.datastore.preference_keys.AMapPreferenceKeys.AMapOptions.KEY_IS_ENABLE_TILT_GESTURE
import io.dev.relic.core.data.datastore.preference_keys.AMapPreferenceKeys.AMapOptions.KEY_IS_ENABLE_ZOOM_CONTROL
import io.dev.relic.core.data.datastore.preference_keys.AMapPreferenceKeys.AMapOptions.KEY_IS_ENABLE_ZOOM_GESTURE
import io.dev.relic.core.data.datastore.preference_keys.AMapPreferenceKeys.MyLocationStyle.KEY_IS_SHOW_MY_LOCATION
import io.dev.relic.core.data.datastore.preference_keys.AMapPreferenceKeys.MyLocationStyle.KEY_MAP_LOCATION_TYPE
import io.dev.relic.global.utils.LogUtil

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
    object MapConfig {

        private const val TAG = "${AMapConfig.TAG}_MapConfig"

        fun defaultConfig(): MyLocationStyle {
            val isShowMyLocation: Boolean = readSyncData(KEY_IS_SHOW_MY_LOCATION, true)
            val locationType: Int = readSyncData(KEY_MAP_LOCATION_TYPE, MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE)

            StringBuilder().apply {
                append("是否显示定位小蓝点: $isShowMyLocation, ")
                append("我的位置展示模式: $locationType")
            }.run {
                LogUtil.debug(TAG, this.toString())
            }

            return MyLocationStyle().apply {
                showMyLocation(isShowMyLocation)
                myLocationType(locationType)
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
            val isEnableCompress: Boolean = readSyncData(KEY_IS_ENABLE_COMPRESS, true)
            val isEnableRotateGesture: Boolean = readSyncData(KEY_IS_ENABLE_ROTATE_GESTURE, true)
            val isEnableScaleControl: Boolean = readSyncData(KEY_IS_ENABLE_SCALE_CONTROL_ENABLE, true)
            val isEnableScrollGesture: Boolean = readSyncData(KEY_IS_ENABLE_SCROLL_GESTURE, true)
            val isEnableTiltGesture: Boolean = readSyncData(KEY_IS_ENABLE_TILT_GESTURE, true)
            val isEnableZoomControl: Boolean = readSyncData(KEY_IS_ENABLE_ZOOM_CONTROL, true)
            val isEnableZoomGesture: Boolean = readSyncData(KEY_IS_ENABLE_ZOOM_GESTURE, true)

            StringBuilder().apply {
                append("是否开启指南针: $isEnableCompress, ")
                append("是否开启手势旋转: $isEnableRotateGesture, ")
                append("是否展示比例尺: $isEnableScaleControl, ")
                append("是否开启手势滑动: $isEnableScrollGesture, ")
                append("是否开启手势倾斜: $isEnableTiltGesture, ")
                append("是否允许缩放: $isEnableZoomControl, ")
                append("是否开启手势缩放: $isEnableZoomGesture")
            }.run {
                LogUtil.debug(TAG, this.toString())
            }

            return AMapOptions().apply {
                compassEnabled(isEnableCompress)
                rotateGesturesEnabled(isEnableRotateGesture)
                scaleControlsEnabled(isEnableScaleControl)
                scrollGesturesEnabled(isEnableScrollGesture)
                tiltGesturesEnabled(isEnableTiltGesture)
                zoomControlsEnabled(isEnableZoomControl)
                zoomGesturesEnabled(isEnableZoomGesture)
            }
        }
    }

}