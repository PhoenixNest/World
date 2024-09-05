package io.core.network.api

import io.data.dto.maxim.MaximDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface IMaximApi {

    @GET
    fun getRandomMaxim(
        @Query("token") token: String,
        @Query("c") category: String,
        @Query("charset") charSet: String
    ): MaximDTO

}