package io.module.map.tomtom.legacy.viewmodel

import com.tomtom.sdk.search.SearchResponse
import com.tomtom.sdk.search.common.error.SearchFailure

interface TomTomMapSearchListener {

    fun onSuccess(result: SearchResponse)

    fun onFailure(failure: SearchFailure)

}