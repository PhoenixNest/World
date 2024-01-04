package io.module.map.amap

import com.amap.api.maps.AMap
import com.amap.api.maps.AMapOptions
import com.amap.api.maps.model.MyLocationStyle
import io.common.util.LogUtil
import io.core.datastore.RelicDatastoreCenter.readSyncData
import io.core.datastore.RelicDatastoreCenter.writeSyncData
import io.core.datastore.preference_keys.AMapPreferenceKeys.AMapOptions.KEY_IS_ENABLE_COMPRESS
import io.core.datastore.preference_keys.AMapPreferenceKeys.AMapOptions.KEY_IS_ENABLE_ROTATE_GESTURE
import io.core.datastore.preference_keys.AMapPreferenceKeys.AMapOptions.KEY_IS_ENABLE_SCALE_CONTROL_ENABLE
import io.core.datastore.preference_keys.AMapPreferenceKeys.AMapOptions.KEY_IS_ENABLE_SCROLL_GESTURE
import io.core.datastore.preference_keys.AMapPreferenceKeys.AMapOptions.KEY_IS_ENABLE_TILT_GESTURE
import io.core.datastore.preference_keys.AMapPreferenceKeys.AMapOptions.KEY_IS_ENABLE_ZOOM_CONTROL
import io.core.datastore.preference_keys.AMapPreferenceKeys.AMapOptions.KEY_IS_ENABLE_ZOOM_GESTURE
import io.core.datastore.preference_keys.AMapPreferenceKeys.MyLocationStyle.KEY_IS_SHOW_MY_LOCATION
import io.core.datastore.preference_keys.AMapPreferenceKeys.MyLocationStyle.KEY_MAP_FETCH_INTERVAL
import io.core.datastore.preference_keys.AMapPreferenceKeys.MyLocationStyle.KEY_MAP_LOCATION_TYPE

/**
 * @see AMapPreferenceKeys
 * */
object AMapConfig {

    private const val TAG = "AMapConfig"

    object ViewConfig {

        private const val TAG = "${AMapConfig.TAG}_AMapViewConfig"

        fun setup(
            aMap: AMap,
            style: MyLocationStyle
        ) {
            aMap.apply {
                myLocationStyle = style
                setMyLocationEnabled(true)
            }
        }

        fun locationChangeListener(): AMap.OnMyLocationChangeListener {
            return AMap.OnMyLocationChangeListener {
                LogUtil.d(TAG, "[当前经纬度]: (${it.longitude}, ${it.altitude})")
            }
        }
    }

    /**
     * [3D地图 • MyLocationStyle](https://lbs.amap.com/api/android-sdk/guide/create-map/mylocation)
     *
     * @see MyLocationStyle
     * */
    object MapStyle {

        private const val TAG = "${AMapConfig.TAG}_MyLocationStyle"

        val myLocationStyle: MyLocationStyle = defaultStyle()

        fun setMyLocationType(type: Int): MyLocationStyle {
            return myLocationStyle.apply {
                LogUtil.d(TAG, "[MyLocationStyle] 我的位置展示模式: $type")
                writeSyncData(KEY_IS_SHOW_MY_LOCATION, type)
                myLocationType(type)
            }
        }

        fun setFetchInterval(interval: Long): MyLocationStyle {
            return myLocationStyle.apply {
                LogUtil.d(TAG, "[MyLocationStyle] 定位间隔: $interval")
                writeSyncData(KEY_MAP_FETCH_INTERVAL, interval)
                interval(interval)
            }
        }

        fun isShowMyLocation(isEnable: Boolean): MyLocationStyle {
            return myLocationStyle.apply {
                LogUtil.d(TAG, "[MyLocationStyle] 是否显示定位小蓝点: $isEnable")
                writeSyncData(KEY_MAP_LOCATION_TYPE, isEnable)
                showMyLocation(isEnable)
            }
        }

        private fun defaultStyle(): MyLocationStyle {
            val fetchInterval: Long = readSyncData(KEY_MAP_FETCH_INTERVAL, 200L)
            val locationType: Int = readSyncData(KEY_MAP_LOCATION_TYPE, MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE)
            val isShowMyLocation: Boolean = readSyncData(KEY_IS_SHOW_MY_LOCATION, true)

            StringBuilder().apply {
                append("[MyLocationStyle 默认配置] 我的位置展示模式: ${locationType.convertLocationType()}")
                append("\n")
                append("[MyLocationStyle 默认配置] 是否显示定位小蓝点: $isShowMyLocation")
            }.run {
                LogUtil.d(TAG, this.toString())
            }

            return MyLocationStyle().apply {
                interval(fetchInterval)
                myLocationType(locationType)
                showMyLocation(isShowMyLocation)
            }
        }

        private fun Int.convertLocationType(): String {
            return when (this) {
                MyLocationStyle.LOCATION_TYPE_SHOW -> "只定位一次"
                MyLocationStyle.LOCATION_TYPE_LOCATE -> "定位一次，且将视角移动到地图中心点"
                MyLocationStyle.LOCATION_TYPE_FOLLOW -> "连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）"
                MyLocationStyle.LOCATION_TYPE_MAP_ROTATE -> "连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）"
                MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE -> "连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。"
                MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER -> "连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。"
                MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER -> "连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动"
                MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER -> "连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动"
                else -> "内部错误"
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

        val options: AMapOptions = defaultConfig()

        fun isEnableCompass(isEnable: Boolean): AMapOptions {
            return options.apply {
                LogUtil.d(TAG, "[AMapOptions] 是否开启指南针: $isEnable")
                writeSyncData(KEY_IS_ENABLE_COMPRESS, true)
                compassEnabled(isEnable)
            }
        }

        fun isEnableRotateGesture(isEnable: Boolean): AMapOptions {
            return options.apply {
                LogUtil.d(TAG, "[AMapOptions] 是否开启手势旋转: $isEnable")
                writeSyncData(KEY_IS_ENABLE_ROTATE_GESTURE, true)
                rotateGesturesEnabled(isEnable)
            }
        }

        fun isEnableScaleControls(isEnable: Boolean): AMapOptions {
            return options.apply {
                LogUtil.d(TAG, "[AMapOptions] 是否展示比例尺: $isEnable")
                writeSyncData(KEY_IS_ENABLE_SCALE_CONTROL_ENABLE, true)
                scaleControlsEnabled(isEnable)
            }
        }

        fun isEnableScrollGesture(isEnable: Boolean): AMapOptions {
            return options.apply {
                LogUtil.d(TAG, "[AMapOptions] 是否开启手势滑动: $isEnable")
                writeSyncData(KEY_IS_ENABLE_SCROLL_GESTURE, true)
                scrollGesturesEnabled(isEnable)
            }
        }

        fun isEnableTiltGestures(isEnable: Boolean): AMapOptions {
            return options.apply {
                LogUtil.d(TAG, "[AMapOptions] 是否开启手势倾斜: $isEnable")
                writeSyncData(KEY_IS_ENABLE_TILT_GESTURE, true)
                tiltGesturesEnabled(isEnable)
            }
        }

        fun isEnableZoomControls(isEnable: Boolean): AMapOptions {
            return options.apply {
                LogUtil.d(TAG, "[AMapOptions] 是否允许缩放: $isEnable")
                writeSyncData(KEY_IS_ENABLE_ZOOM_CONTROL, true)
                zoomControlsEnabled(isEnable)
            }
        }

        fun isEnableZoomGestures(isEnable: Boolean): AMapOptions {
            return options.apply {
                LogUtil.d(TAG, "[AMapOptions] 是否开启手势缩放: $isEnable")
                writeSyncData(KEY_IS_ENABLE_ZOOM_GESTURE, true)
                zoomGesturesEnabled(isEnable)
            }
        }

        private fun defaultConfig(): AMapOptions {
            val isEnableCompress: Boolean = readSyncData(KEY_IS_ENABLE_COMPRESS, true)
            val isEnableRotateGesture: Boolean = readSyncData(KEY_IS_ENABLE_ROTATE_GESTURE, true)
            val isEnableScaleControl: Boolean = readSyncData(KEY_IS_ENABLE_SCALE_CONTROL_ENABLE, true)
            val isEnableScrollGesture: Boolean = readSyncData(KEY_IS_ENABLE_SCROLL_GESTURE, true)
            val isEnableTiltGesture: Boolean = readSyncData(KEY_IS_ENABLE_TILT_GESTURE, true)
            val isEnableZoomControl: Boolean = readSyncData(KEY_IS_ENABLE_ZOOM_CONTROL, true)
            val isEnableZoomGesture: Boolean = readSyncData(KEY_IS_ENABLE_ZOOM_GESTURE, true)

            StringBuilder().apply {
                append("[AMapOptions 默认配置] 是否开启指南针: $isEnableCompress")
                append("\n")
                append("[AMapOptions 默认配置] 是否开启手势旋转: $isEnableRotateGesture")
                append("\n")
                append("[AMapOptions 默认配置] 是否展示比例尺: $isEnableScaleControl")
                append("\n")
                append("[AMapOptions 默认配置] 是否开启手势滑动: $isEnableScrollGesture")
                append("\n")
                append("[AMapOptions 默认配置] 是否开启手势倾斜: $isEnableTiltGesture")
                append("\n")
                append("[AMapOptions 默认配置] 是否允许缩放: $isEnableZoomControl")
                append("\n")
                append("[AMapOptions 默认配置] 是否开启手势缩放: $isEnableZoomGesture")
            }.run {
                LogUtil.d(TAG, this.toString())
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