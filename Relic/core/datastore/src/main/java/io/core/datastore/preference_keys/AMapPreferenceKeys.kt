package io.core.datastore.preference_keys

object AMapPreferenceKeys {

    /**
     * [3D地图 • AMapOptions](https://a.amap.com/lbs/static/unzip/Android_Map_Doc/index.html)
     * */
    object MyLocationStyle {

        /**
         * 设置我的位置展示模式，模式分别为：
         *
         * - LOCATION_TYPE_SHOW：只定位一次。
         * - LOCATION_TYPE_LOCATE：定位一次，且将视角移动到地图中心点。
         * - LOCATION_TYPE_FOLLOW：连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
         * - LOCATION_TYPE_MAP_ROTATE：连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
         * - LOCATION_TYPE_LOCATION_ROTATE：连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
         * - LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER：连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
         * - LOCATION_TYPE_FOLLOW_NO_CENTER：连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
         * - LOCATION_TYPE_MAP_ROTATE_NO_CENTER：连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。
         * */
        const val KEY_MAP_LOCATION_TYPE = "key_map_location_type"

        /**
         * 定位间隔
         * */
        const val KEY_MAP_FETCH_INTERVAL = "key_map_fetch_interval"

        /**
         * 设置是否显示定位小蓝点。
         *
         * - true 显示
         * - false 不显示。
         * */
        const val KEY_IS_SHOW_MY_LOCATION = "key_is_show_my_location"
    }

    /**
     * [3D地图 • AMapOptions](https://a.amap.com/lbs/static/unzip/Android_Map_Doc/index.html)
     * */
    object AMapOptions {

        /**
         * 设置指南针是否可用。
         * */
        const val KEY_IS_ENABLE_COMPRESS = "key_is_enable_compress"

        /**
         * 设置地图是否可以通过手势进行旋转。
         * */
        const val KEY_IS_ENABLE_ROTATE_GESTURE = "key_is_enable_rotate_gesture"

        /**
         * 设置地图是否显示比例尺，默认为false。
         * */
        const val KEY_IS_ENABLE_SCALE_CONTROL_ENABLE = "key_is_enable_scale_control_enable"

        /**
         * 设置地图是否可以通过手势滑动
         * */
        const val KEY_IS_ENABLE_SCROLL_GESTURE = "key_is_enable_scroll_gesture"

        /**
         * 设置地图是否可以通过手势倾斜（3D效果），默认为true。
         * */
        const val KEY_IS_ENABLE_TILT_GESTURE = "key_is_enable_tilt_gesture"

        /**
         * 设置地图是否允许缩放。
         * */
        const val KEY_IS_ENABLE_ZOOM_CONTROL = "key_is_enable_zoom_control"

        /**
         * 设置地图是否可以通过手势进行缩放。
         * */
        const val KEY_IS_ENABLE_ZOOM_GESTURE = "key_is_enable_zoom_gesture"
    }

}