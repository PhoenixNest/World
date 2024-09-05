package io.module.map.tomtom

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import com.tomtom.sdk.common.measures.UnitSystem
import com.tomtom.sdk.map.display.TomTomMap
import com.tomtom.sdk.map.display.camera.CameraTrackingMode
import com.tomtom.sdk.map.display.location.LocationMarkerOptions
import com.tomtom.sdk.map.display.ui.MapFragment
import com.tomtom.sdk.map.display.ui.currentlocation.CurrentLocationButton
import com.tomtom.sdk.map.display.ui.logo.LogoView

object TomTomMapCustomizer {

    /* ======================== Map ui ======================== */

    /**
     *
     * [Zoom controls](https://developer.tomtom.com/maps/android/guides/map-display/ui-controls#zoom-controls)
     *
     * The camera zoom can be set between 0 and 22. Approximate zoom scales:
     *
     * - 0 - The camera is fully zoomed out the whole globe is visible.
     * - 5 - Country-level.
     * - 10 - City-level.
     * - 15 - Neighborhood-level.
     * - 20 - Street-level.
     * - 22 - Maximum zoom in.
     * */
    const val DEFAULT_ZOOM_VALUE = 15.0

    /**
     * [Tilt](https://developer.tomtom.com/maps/android/guides/map-display/camera-and-animations#tilt)
     *
     * The tilt is the angle from the nadir (the camera pointing toward the center of the earth),
     * where the tilt is set to zero 0. When the tilt is 90, the camera is pointing towards the
     * horizon.
     * */
    const val DEFAULT_VIEW_TILE = 30.0

    /**
     * [Scale view](https://developer.tomtom.com/maps/android/guides/map-display/ui-controls#scale-view)
     *
     * The scale view is located in the lower right corner by default.
     * It appears when zooming in or out, then disappears after 5 seconds without any map interaction.
     * It also disappears when the map is tilted or when the scale is out of range.
     *
     * `The scale range is 10 m to 1000 km (metric) / 30 ft to 620 mi (imperial).`
     * */
    const val DEFAULT_IS_SHOW_SCALE_VIEW = true

    /**
     * [Zoom Control](https://developer.tomtom.com/maps/android/guides/map-display/ui-controls#zoom-controls)
     *
     * When visible, the zoom control is in the middle of the right-hand side of the map.
     * It is invisible by default, but you can change it using the isVisible flag on the ZoomControlsView.
     * */
    const val DEFAULT_IS_ENABLE_ZOOM_CONTROL_VIEW = false

    /**
     * [UnitSystem](https://developer.tomtom.com/assets/downloads/tomtom-sdks/android/api-reference/0.50.6/common/core/com.tomtom.sdk.common.measures/-unit-system/index.html)
     *
     * Preferred system of measurement. Choose UnitSystem.Metric for the metric system (meters, kilometers),
     * or, for the imperial system, UnitSystem.UK (miles, yards) or UnitSystem.US (miles, feet).
     * */
    val DEFAULT_SCALE_VIEW_UNIT = UnitSystem.Metric

    /**
     * [Location Marker Type](https://developer.tomtom.com/maps/android/guides/map-display/user-location#locationmarkertype)
     *
     * This parameter specifies the type of the marker. It is required. The Map Display module provides two default markers:
     *
     * - LocationMarkerType.Pointer - Location marker rendered as a point.
     * - LocationMarkerType.Chevron - Location marker rendered as a chevron.
     * */
    val DEFAULT_LOCATION_MARKER_TYPE = LocationMarkerOptions.Type.Pointer

    /**
     * [Current location button](https://developer.tomtom.com/maps/android/guides/map-display/ui-controls#current-location-button)
     *
     * Users can click the current location button to re-center the camera on the device location.
     * */
    val DEFAULT_LOCATION_BUTTON_POLICY = CurrentLocationButton.VisibilityPolicy.Visible

    /**
     * [Logo view](https://developer.tomtom.com/maps/android/guides/map-display/ui-controls#logo-view)
     *
     * The TomTom logo is displayed by default in the bottom left corner of the map.
     * */
    val DEFAULT_IS_SHOW_TOMTOM_LOGO = LogoView.VisibilityPolicy.Visible

    /* ======================== Map camera ======================== */

    /**
     * [Tracking mode](https://developer.tomtom.com/maps/android/guides/map-display/camera-and-animations#tracking-mode)
     *
     * The Map Display module allows you to set how the camera tracks the user locations to
     * suit different interaction modes. Do this by setting CameraTrackingMode to the TomTomMap instance. There are five options for tracking mode:
     *
     * - None - The camera does not track the user’s location. This is the default setting and is mainly used to show the user’s location on the map.
     * - FollowNorthUp - The camera follows the user’s location, but its tilt and zoom do not change.
     * - FollowRouteDirection - The camera follows the user’s location and heading to best present the route. Camera properties like tilt and zoom may be adjusted to better display the route and its guidance instructions.
     * - FollowDirection - The camera follows the user’s location and heading to position the camera in the heading direction. Camera is always following from the top (the tilt is set to 0 degrees).
     * - FollowOverview - The camera tries to fit the routes in the current view, by changing the zoom level and other camera properties.
     *
     * */
    val DEFAULT_TRACKING_MODE = CameraTrackingMode.None

    /* ======================== Traffic ======================== */

    /**
     * [Traffic](https://developer.tomtom.com/maps/android/guides/map-display/traffic)
     *
     * The TomTom Map Display module provides real-time traffic updates.
     * You can show this on the map by adding a layer with traffic flow
     * and a layer with traffic incidents.
     * */
    private const val DEFAULT_IS_SHOW_TRAFFIC_INFO = false

    /**
     * [Vehicle Restrictions](https://developer.tomtom.com/maps/android/guides/map-display/vehicle-restrictions)
     *
     * Vehicle restrictions are used commonly for trucks, delivery vans, and scooter drivers.
     * Climate/pollution regulations are also introducing more rules for cars, such as electric
     * vehicles (EVs) and cars that fail to meet various low-emission vehicle requirements.
     * */
    private const val DEFAULT_IS_SHOW_VEHICLE_RESTRICTIONS_INFO = false

    /* ======================== Map customizer ======================== */

    fun MapFragment.toggleZoomControl(isShowUi: Boolean = DEFAULT_IS_ENABLE_ZOOM_CONTROL_VIEW) {
        zoomControlsView.isVisible = isShowUi
    }

    fun MapFragment.toggleScaleControl(
        isShowUi: Boolean = DEFAULT_IS_SHOW_SCALE_VIEW,
        scaleUnit: UnitSystem = DEFAULT_SCALE_VIEW_UNIT
    ) {
        scaleView.isVisible = isShowUi
        scaleView.units = scaleUnit
    }

    fun TomTomMap.toggleTrafficMode(
        isShowTrafficInfo: Boolean = DEFAULT_IS_SHOW_TRAFFIC_INFO,
        isShowVehicleRestrictionsInfo: Boolean = DEFAULT_IS_SHOW_VEHICLE_RESTRICTIONS_INFO
    ) {
        if (isShowTrafficInfo) {
            showTrafficFlow()
            showTrafficIncidents()
        }

        if (isShowVehicleRestrictionsInfo) {
            showVehicleRestrictions()
        }
    }

    fun isDarkTheme(activity: AppCompatActivity): Boolean {
        val uiMode = activity.resources.configuration.uiMode
        return (uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    }
}