package io.dev.relic.core.data.datastore.preference_keys

object AMapPreferenceKeys {

    /**
     * [AMapOptions](https://a.amap.com/lbs/static/unzip/Android_Map_Doc/index.html)
     * */
    object AmapOptions {

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