package io.core.network.api

import io.data.dto.maxim.MaximDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface IMaximApi {

    /**
     * [语句接口](https://developer.hitokoto.cn/sentence/)
     *
     * @param token         请求密钥
     * @param category      分类，取值如下：
     * - a	动画
     * - b	漫画
     * - c	游戏
     * - d	文学
     * - e	原创
     * - f	来自网络
     * - g	其他
     * - h	影视
     * - i	诗词
     * - j	网易云
     * - k	哲学
     * - l	抖机灵
     * - 其他	作为 动画 类型处理
     * @param charSet      字符集，取值如下：
     * - utf-8	返回 utf-8 编码的内容
     * - gbk	返回 gbk 编码的内容。不支持与异步函数同用
     * - 其他	返回 utf-8 编码的内容
     * */
    @GET("/")
    suspend fun getRandomMaxim(
        @Query("token") token: String,
        @Query("c") category: String,
        @Query("charset") charSet: String
    ): MaximDTO

}