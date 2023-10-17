package io.dev.relic.domain.location.map.amap

import android.content.Context
import com.amap.api.maps.MapsInitializer
import com.amap.api.services.core.ServiceSettings

object AMapPrivacyCenter {

    private const val TAG = "AMapPrivacyCenter"

    /**
     * 核验是否同意地图合规政策。
     *
     * @param context   上下文
     *
     * @see updatePrivacyShow
     * @see updatePrivacyAgree
     * */
    fun verifyAMapPrivacyAgreement(
        context: Context,
        isShowUserAgreement: Boolean,
        isAgreeUserPrivacy: Boolean
    ) {
        updatePrivacyShow(
            context = context,
            isContains = true,
            isShow = isShowUserAgreement
        )
        updatePrivacyAgree(
            context = context,
            isAgree = isAgreeUserPrivacy
        )
    }

    /**
     * 更新隐私合规状态,需要在初始化地图之前完成。
     *
     * [Android 地图SDK • 隐私合规接口说明](https://lbs.amap.com/api/android-sdk/guide/create-project/dev-attention#t2)
     *
     * [3D地图 • MapsInitializer](https://a.amap.com/lbs/static/unzip/Android_Map_Doc/index.html)
     *
     * [搜索服务 • ServiceSettings](https://a.amap.com/lbs/static/unzip/Android_Map_Doc/index.html)
     *
     * @param  context: 上下文
     * @param  isContains: 隐私权政策是否包含高德开平隐私权政策，true是包含
     * @param  isShow: 隐私权政策是否弹窗展示告知用户，true是展示
     * */
    private fun updatePrivacyShow(
        context: Context,
        isContains: Boolean,
        isShow: Boolean
    ) {
        MapsInitializer.updatePrivacyShow(context, isContains, isShow)
        ServiceSettings.updatePrivacyShow(context, isContains, isShow)
    }

    /**
     * 更新同意隐私状态,需要在初始化地图之前完成。
     *
     * [Android 地图SDK • 隐私合规接口说明](https://lbs.amap.com/api/android-sdk/guide/create-project/dev-attention#t2)
     *
     * [3D地图 • MapsInitializer](https://a.amap.com/lbs/static/unzip/Android_Map_Doc/index.html)
     *
     * [搜索服务 • ServiceSettings](https://a.amap.com/lbs/static/unzip/Android_Map_Doc/index.html)
     *
     * @param context: 上下文
     * @param isAgree: 隐私权政策是否取得用户同意，true是用户同意
     * */
    private fun updatePrivacyAgree(
        context: Context,
        isAgree: Boolean
    ) {
        MapsInitializer.updatePrivacyAgree(context, isAgree)
        ServiceSettings.updatePrivacyAgree(context, isAgree)
    }

}