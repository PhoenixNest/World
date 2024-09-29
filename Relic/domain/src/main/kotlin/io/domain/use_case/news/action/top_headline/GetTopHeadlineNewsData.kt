package io.domain.use_case.news.action.top_headline

import io.core.network.NetworkParameters.Keys.NEWS_API_DEV_KEY
import io.data.dto.news.top_headlines.TopHeadlinesNewsDTO
import io.data.model.NetworkResult
import io.data.util.NewsCategory
import io.data.util.NewsConfig.DEFAULT_KEY_WORDS
import io.data.util.NewsCountryType
import io.domain.repository.INewsDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Locale
import javax.inject.Inject

class GetTopHeadlineNewsData @Inject constructor(
    private val newsRepository: INewsDataRepository
) {

    /**
     * This endpoint provides live top and breaking headlines for a country,
     * specific category in a country, single source, or multiple sources.
     * You can also search with keywords.
     *
     * `Articles are sorted by the earliest date published first`.
     *
     * @param apiKey            `Your API key.` Alternatively you can provide this via the X-Api-Key HTTP header.
     * @param keyWords          Keywords or a phrase to search for.
     * @param country           The 2-letter ISO 3166-1 code of the country you want to get headlines for.
     *                          `Possible options: ae, ar, at, au, be, bg, br, ca, ch, cn, co, cu, cz, de, eg, fr, gb, gr, hk, hu, id, ie, il, in, it, jp, kr, lt, lv, ma, mx, my, ng, nl, no, nz, ph, pl, pt, ro, rs, ru, sa, se, sg, si, sk, th, tr, tw, ua, us, ve, za.`
     *                          Note: you can't mix this param with the sources param.
     * @param category          The category you want to get headlines for.
     *                          `Possible options: business, entertainment, general, health, science, sports, technology.`
     *                          Note: you can't mix this param with the sources param.
     * @param pageSize          The number of results to return per page.
     * @param page              Use this to page through the results.
     * */
    operator fun invoke(
        apiKey: String = NEWS_API_DEV_KEY,
        keyWords: String,
        country: NewsCountryType,
        category: NewsCategory,
        pageSize: Int,
        page: Int
    ): Flow<NetworkResult<TopHeadlinesNewsDTO>> {
        return flow {
            val result = newsRepository.getTopHeadlinesNews(
                apiKey = apiKey,
                keyWords = if (keyWords == DEFAULT_KEY_WORDS) "" else keyWords,
                country = country.name.lowercase(Locale.getDefault()),
                category = category.name.lowercase(Locale.getDefault()),
                pageSize = pageSize,
                page = page
            )
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

}