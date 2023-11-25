package io.module.map.amap

import android.content.Context
import com.amap.api.services.poisearch.PoiSearchV2
import com.amap.api.services.poisearch.PoiSearchV2.OnPoiSearchListener

object AMapPoiCenter {

    /**
     * [获取POI数据 • 关键字检索](https://lbs.amap.com/api/android-sdk/guide/map-data/poi#keywords)
     *
     * [搜索服务 • PoiResultV2](https://a.amap.com/lbs/static/unzip/Android_Map_Doc/index.html)
     *
     * @param context           上下文
     * @param keyWord           表示搜索字符串
     * @param poiSearchType     POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码
     * @param cityCode          表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
     * @param offset            当前查询页码
     *
     * @see PoiSearchV2
     * */
    fun fetchPoiData(
        context: Context,
        keyWord: String,
        poiSearchType: String,
        cityCode: String,
        offset: Int,
        listener: OnPoiSearchListener
    ) {
        val query = PoiSearchV2.Query(keyWord, poiSearchType, cityCode)
        // 设置每页最多返回多少条poiItem
        query.pageSize = 10
        // 设置查询页码
        query.pageNum = offset

        val poiSearchV2 = PoiSearchV2(context, query)
        poiSearchV2.setOnPoiSearchListener(listener)
        poiSearchV2.searchPOIAsyn()
    }

}