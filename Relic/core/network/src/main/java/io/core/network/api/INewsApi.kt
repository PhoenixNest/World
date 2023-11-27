package io.core.network.api

import io.data.dto.news.everything.NewsEverythingDTO
import io.data.dto.news.top_headlines.NewsTopHeadlinesDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface INewsApi {

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
    @GET("everything?")
    suspend fun fetchEverythingNews(
        @Query("apiKey") apiKey: String,
        @Query("q") keyWords: String,
        @Query("source") source: String,
        @Query("language") language: String,
        @Query("sortBy") sortBy: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int
    ): NewsEverythingDTO

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
    @GET("top-headlines?")
    suspend fun fetchTopHeadlinesNews(
        @Query("apiKey") apiKey: String,
        @Query("q") keyWords: String,
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int
    ): NewsTopHeadlinesDTO

}