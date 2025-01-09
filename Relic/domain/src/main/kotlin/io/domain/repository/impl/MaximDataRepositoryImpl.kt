package io.domain.repository.impl

import io.core.network.api.IMaximApi
import io.data.dto.maxim.MaximDTO
import io.data.model.NetworkResult
import io.domain.repository.IMaximDataRepository
import javax.inject.Inject

/**
 * @see IMaximDataRepository
 * */
class MaximDataRepositoryImpl @Inject constructor(
    private val maximApi: IMaximApi
) : IMaximDataRepository {

    private var maximResult: NetworkResult<MaximDTO> = NetworkResult.Loading()

    companion object {
        private const val TAG = "MaximDataRepository"
    }

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
    override suspend fun getRandomMaxim(
        token: String,
        category: String,
        charSet: String
    ): NetworkResult<MaximDTO> {
        maximResult = try {
            val data = maximApi.getRandomMaxim(
                token = token,
                category = category,
                charSet = charSet
            )

            NetworkResult.Success(data)
        } catch (exception: Exception) {
            exception.printStackTrace()
            NetworkResult.Failed(message = exception.message ?: "Unknown error occurred.")
        }

        return maximResult
    }
}