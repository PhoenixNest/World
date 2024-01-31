package io.domain.use_case.news.action.everything

import io.core.network.NetworkParameters.Keys.NEWS_API_DEV_KEY
import io.data.dto.news.everything.TrendingNewsDTO
import io.data.model.NetworkResult
import io.data.util.NewsLanguageType
import io.data.util.NewsSortRule
import io.domain.repository.INewsDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Locale
import javax.inject.Inject

class GetTrendingNewsData @Inject constructor(
    private val newsRepository: INewsDataRepository
) {

    /**
     * Search through millions of articles from over 80,000 large and small news sources and blogs.
     *
     * `This endpoint suits article discovery and analysis.`
     *
     * @param apiKey            `Your API key.` Alternatively you can provide this via the X-Api-Key HTTP header.
     * @param keyWords          Keywords or phrases to search for in the article title and body. `Max length: 500 chars.`
     * @param source            A comma-seperated string of identifiers (maximum 20) for the news sources or blogs you want headlines from.
     *                          Use the /sources endpoint to locate these programmatically or look at the [sources index](https://newsapi.org/sources).
     * @param language          The 2-letter ISO-639-1 code of the language you want to get headlines for.
     *                          `Possible options: ar, de, en, es, fr, he, it, nl, no, pt, ru, sv, ud, zh.`
     * @param sortBy            The order to sort the articles in. `Possible options: relevancy, popularity, publishedAt`.
     *                          (1) relevancy = articles more closely related to q come first.
     *                          (2) popularity = articles from popular sources and publishers come first.
     *                          (3) publishedAt = newest articles come first.
     * @param pageSize          The number of results to return per page.
     * @param page              Use this to page through the results.
     * */
    operator fun invoke(
        apiKey: String = NEWS_API_DEV_KEY,
        keyWords: String,
        source: String,
        language: NewsLanguageType,
        sortBy: NewsSortRule,
        pageSize: Int,
        page: Int
    ): Flow<NetworkResult<TrendingNewsDTO>> {
        return flow {
            val result = newsRepository.getTrendingNews(
                apiKey = apiKey,
                keyWords = keyWords,
                source = source,
                language = language.name.lowercase(Locale.getDefault()),
                sortBy = sortBy.ruleString.lowercase(Locale.getDefault()),
                pageSize = pageSize,
                page = page
            )
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

}