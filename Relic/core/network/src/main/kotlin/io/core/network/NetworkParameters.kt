package io.core.network

import io.common.RelicResCenter.getString

object NetworkParameters {

    /**
     * Parameters of the okHttpClient constructor builder.
     *
     * TimeUnit: seconds
     * */
    const val MAX_TIMEOUT_CONNECT_DURATION = 30L
    const val MAX_TIMEOUT_CALL_DURATION = 30L
    const val MAX_TIMEOUT_READ_DURATION = 30L
    const val MAX_TIMEOUT_WRITE_DURATION = 30L

    /**
     * The max duration of the offline-cache.
     *
     * TimeUnit: hours
     * */
    const val MAX_OFFLINE_CACHE_TIME = 24

    /**
     * The max duration of the online-cache.
     *
     * TimeUnit: seconds
     * */
    const val MAX_ONLINE_CACHE_TIME = 30 * 60

    /**
     * The max retry times for the network request.
     * */
    const val MAX_RETRY_TIMES = 3

    /**
     * The max disk size of offline cache.
     * */
    const val MAX_DISK_CACHE_SIZE = 10 * 1024 * 1024L

    object BaseUrl {

        /**
         * [Open-Meteo Api](https://open-meteo.com/)
         * */
        const val WEATHER_API_URL = "https://api.open-meteo.com/v1/"

        /**
         * [Spoonacular API](https://spoonacular.com/food-api)
         * */
        const val FOOD_RECIPES_API_URL = "https://api.spoonacular.com/recipes/"

        /**
         * [News Api](https://newsapi.org/docs)
         * */
        const val NEWS_API_URL = "https://newsapi.org/v2/"

        /**
         * [Pixabay Api](https://pixabay.com/api/docs/)
         * */
        const val PIXABAY_API_URL = "https://pixabay.com/"

        /**
         * [Hitokoto Api](https://developer.hitokoto.cn/)
         * */
        const val HITOKOTO_API_URL = "https://international.v1.hitokoto.cn/"
    }

    object Keys {
        val FOOD_RECIPES_API_DEV_KEY = getString(R.string.spoonacular_dev_key)
        val HITOKOTO_API_DEV_KEY = getString(R.string.hitokoto_dev_key)
        val NEWS_API_DEV_KEY = getString(R.string.news_dev_key)
        val PIXABAY_API_KEY = getString(R.string.pixabay_dev_key)
    }

}