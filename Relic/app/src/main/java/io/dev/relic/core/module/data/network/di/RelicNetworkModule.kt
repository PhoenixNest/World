package io.dev.relic.core.module.data.network.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.dev.relic.core.module.data.network.api.IFoodRecipesApi
import io.dev.relic.core.module.data.network.api.IWeatherApi
import io.dev.relic.core.module.data.network.di.NetworkParameters.BaseUrl.FOOD_RECIPES_API_URL
import io.dev.relic.core.module.data.network.di.NetworkParameters.BaseUrl.WEATHER_API_URL
import io.dev.relic.core.module.data.network.di.NetworkParameters.MAX_DISK_CACHE_SIZE
import io.dev.relic.core.module.data.network.di.NetworkParameters.MAX_OFFLINE_CACHE_TIME
import io.dev.relic.core.module.data.network.di.NetworkParameters.MAX_ONLINE_CACHE_TIME
import io.dev.relic.core.module.data.network.di.NetworkParameters.MAX_RETRY_TIMES
import io.dev.relic.core.module.data.network.di.NetworkParameters.MAX_TIMEOUT_CALL_DURATION
import io.dev.relic.core.module.data.network.di.NetworkParameters.MAX_TIMEOUT_CONNECT_DURATION
import io.dev.relic.core.module.data.network.di.NetworkParameters.MAX_TIMEOUT_READ_DURATION
import io.dev.relic.core.module.data.network.di.NetworkParameters.MAX_TIMEOUT_WRITE_DURATION
import io.dev.relic.core.module.data.network.interceptor.*
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

object NetworkParameters {

    object BaseUrl {

        /**
         * [Open-Meteo Api](https://open-meteo.com/)
         * */
        const val WEATHER_API_URL: String = "https://api.open-meteo.com/"

        /**
         * [Spoonacular API](https://spoonacular.com/food-api)
         * */
        const val FOOD_RECIPES_API_URL: String = "https://api.spoonacular.com/recipes/complexSearch"

    }

    /**
     * Parameters of the okHttpClient constructor builder.
     *
     * TimeUnit: seconds
     * */
    const val MAX_TIMEOUT_CONNECT_DURATION: Long = 15
    const val MAX_TIMEOUT_CALL_DURATION: Long = 15
    const val MAX_TIMEOUT_READ_DURATION: Long = 15
    const val MAX_TIMEOUT_WRITE_DURATION: Long = 15

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
    const val MAX_RETRY_TIMES: Int = 10

    /**
     * The max disk size of offline cache.
     * */
    const val MAX_DISK_CACHE_SIZE: Long = 10 * 1024 * 1024

}

@Module
@InstallIn(SingletonComponent::class)
object RelicNetworkModule {

    @Provides
    @Singleton
    fun provideAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideLogInterceptor(): LogInterceptor {
        return LogInterceptor()
    }

    @Provides
    @Singleton
    fun provideOfflineCacheInterceptor(
        @ApplicationContext context: Context
    ): OfflineCacheInterceptor {
        return OfflineCacheInterceptor.Builder()
            .setApplicationContext(context)
            .setMaxOfflineCacheDuration(MAX_OFFLINE_CACHE_TIME)
            .setOfflineCacheTimeUnit(TimeUnit.HOURS)
            .build()
    }

    @Provides
    @Singleton
    fun provideOnlineCacheInterceptor(): OnlineCacheInterceptor {
        return OnlineCacheInterceptor.Builder()
            .setMaxOnlineCacheTimeDuration(MAX_ONLINE_CACHE_TIME)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetryInterceptor(): RetryInterceptor {
        return RetryInterceptor.Builder()
            .setMaxRetryTimes(MAX_RETRY_TIMES)
            .build()
    }

    @Provides
    @Singleton
    fun provideGsonConverter(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideMoshiConvertor(): MoshiConverterFactory {
        return MoshiConverterFactory.create()
    }

    /**
     * Provide OkHttpClient to network module.
     *
     * @param logInterceptor
     * @param authInterceptor
     * @param retryInterceptor
     * @param offlineCacheInterceptor
     * @param onlineCacheInterceptor
     * */
    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        authInterceptor: AuthInterceptor,
        logInterceptor: LogInterceptor,
        offlineCacheInterceptor: OfflineCacheInterceptor,
        onlineCacheInterceptor: OnlineCacheInterceptor,
        retryInterceptor: RetryInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(MAX_TIMEOUT_CONNECT_DURATION, TimeUnit.SECONDS)
            .callTimeout(MAX_TIMEOUT_CALL_DURATION, TimeUnit.SECONDS)
            .readTimeout(MAX_TIMEOUT_READ_DURATION, TimeUnit.SECONDS)
            .writeTimeout(MAX_TIMEOUT_WRITE_DURATION, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .addInterceptor(logInterceptor)
            .addInterceptor(offlineCacheInterceptor)
            .addNetworkInterceptor(onlineCacheInterceptor)
            .addInterceptor(retryInterceptor)
            .cache(Cache(context.cacheDir, MAX_DISK_CACHE_SIZE))
            .build()
    }

    /**
     * Provide the Weather Retrofit client instance with OkHttpClient.
     *
     * @param okHttpClient
     * @param gsonConverterFactory
     * @param moshiConverterFactory
     * */
    @Provides
    @Singleton
    fun provideWeatherApi(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        moshiConverterFactory: MoshiConverterFactory
    ): IWeatherApi {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(WEATHER_API_URL)
            .addConverterFactory(gsonConverterFactory)
            .addConverterFactory(moshiConverterFactory)
            .build()
            .create()
    }

    /**
     * Provide the Weather Retrofit client instance with OkHttpClient.
     *
     * @param okHttpClient
     * @param gsonConverterFactory
     * @param moshiConverterFactory
     * */
    @Provides
    @Singleton
    fun provideRecipesApi(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        moshiConverterFactory: MoshiConverterFactory
    ): IFoodRecipesApi {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(FOOD_RECIPES_API_URL)
            .addConverterFactory(gsonConverterFactory)
            .addConverterFactory(moshiConverterFactory)
            .build()
            .create()
    }

}