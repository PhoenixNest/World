package io.core.network

import io.common.RelicResCenter

object NetworkParameters {

    object BaseUrl {

        /**
         * [Open-Meteo Api](https://open-meteo.com/)
         * */
        const val WEATHER_API_URL: String = "https://api.open-meteo.com/v1/"

        /**
         * [Spoonacular API](https://spoonacular.com/food-api)
         * */
        const val FOOD_RECIPES_API_URL: String = "https://api.spoonacular.com/recipes/"

        /**
         * [News Api](https://newsapi.org/docs)
         * */
        const val NEWS_API_URL: String = "https://newsapi.org/v2/"
    }

    object Keys {
        val FOOD_RECIPES_API_DEV_KEY: String = RelicResCenter.getString(R.string.spoonacular_dev_key)
        val NEWS_API_DEV_KEY: String = RelicResCenter.getString(R.string.news_dev_key)
    }

    /**
     * Parameters of the okHttpClient constructor builder.
     *
     * TimeUnit: seconds
     * */
    const val MAX_TIMEOUT_CONNECT_DURATION: Long = 30
    const val MAX_TIMEOUT_CALL_DURATION: Long = 30
    const val MAX_TIMEOUT_READ_DURATION: Long = 30
    const val MAX_TIMEOUT_WRITE_DURATION: Long = 30

    /**
     * The max duration of the offline-cache.
     *
     * TimeUnit: hours
     * */
    const val MAX_OFFLINE_CACHE_TIME: Int = 24

    /**
     * The max duration of the online-cache.
     *
     * TimeUnit: seconds
     * */
    const val MAX_ONLINE_CACHE_TIME: Int = 30 * 60

    /**
     * The max retry times for the network request.
     * */
    const val MAX_RETRY_TIMES: Int = 3

    /**
     * The max disk size of offline cache.
     * */
    const val MAX_DISK_CACHE_SIZE: Long = 10 * 1024 * 1024

}