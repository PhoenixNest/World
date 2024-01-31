package io.domain.repository.impl

import io.core.network.api.INewsApi
import io.data.dto.news.everything.TrendingNewsDTO
import io.data.dto.news.top_headlines.TopHeadlinesNewsDTO
import io.data.model.NetworkResult
import io.domain.repository.INewsDataRepository
import javax.inject.Inject

class NewsDataRepositoryImpl @Inject constructor(
    private val newsApi: INewsApi
) : INewsDataRepository {

    private var everythingResult: NetworkResult<TrendingNewsDTO> = NetworkResult.Loading()

    private var topHeadlinesResult: NetworkResult<TopHeadlinesNewsDTO> = NetworkResult.Loading()

    companion object {
        private const val TAG = "NewsDataRepository"
    }

    /**
     * [Everything](https://newsapi.org/docs/endpoints/everything)
     *
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
    override suspend fun getTrendingNews(
        apiKey: String,
        keyWords: String,
        source: String,
        language: String,
        sortBy: String,
        pageSize: Int,
        page: Int
    ): NetworkResult<TrendingNewsDTO> {
        everythingResult = try {
            val data = newsApi.getTrendingNews(
                apiKey = apiKey,
                keyWords = keyWords,
                source = source,
                language = language,
                sortBy = sortBy,
                pageSize = pageSize,
                page = page
            )

            NetworkResult.Success(data)
        } catch (exception: Exception) {
            exception.printStackTrace()
            NetworkResult.Failed(message = exception.message ?: "Unknown error occurred.")
        }

        return everythingResult
    }

    /**
     * [Top headlines](https://newsapi.org/docs/endpoints/top-headlines)
     *
     * This endpoint provides live top and breaking headlines for a country,
     * specific category in a country, single source, or multiple sources.
     * You can also search with keywords.
     *
     * `Articles are sorted by the earliest date published first`.
     *
     * @param apiKey            `Your API key.` Alternatively you can provide this via the X-Api-Key HTTP header.
     * @param keyWords          Keywords or a phrase to search for.
     * @param country           The 2-letter ISO 3166-1 code of the country you want to get headlines for.
     *                          `Possible options: ae, ar, at, au, be, bg, br, ca, ch, cn, co, cu, cz, de, eg, fr, gb, gr, hk, hu, id, ie, il, in, it, jp, kr,
     *                          lt, lv, ma, mx, my, ng, nl, no, nz, ph, pl, pt, ro, rs, ru, sa, se, sg, si, sk, th, tr, tw, ua, us, ve, za.`
     *                          Note: you can't mix this param with the sources param.
     * @param category          The category you want to get headlines for.
     *                          `Possible options: business, entertainment, general, health, science, sports, technology.`
     *                          Note: you can't mix this param with the sources param.
     * @param pageSize          The number of results to return per page.
     * @param page              Use this to page through the results.
     * */
    override suspend fun getTopHeadlinesNews(
        apiKey: String,
        keyWords: String,
        country: String,
        category: String,
        pageSize: Int,
        page: Int
    ): NetworkResult<TopHeadlinesNewsDTO> {
        topHeadlinesResult = try {
            val data = newsApi.getTopHeadlinesNews(
                apiKey = apiKey,
                keyWords = keyWords,
                country = country,
                category = category,
                pageSize = pageSize,
                page = page
            )

            NetworkResult.Success(data = data)
        } catch (exception: Exception) {
            exception.printStackTrace()
            NetworkResult.Failed(message = exception.message ?: "Unknown error occurred.")
        }

        return topHeadlinesResult
    }
}