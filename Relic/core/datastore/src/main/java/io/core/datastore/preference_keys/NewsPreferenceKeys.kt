package io.core.datastore.preference_keys

object NewsPreferenceKeys {

    /**
     * Indicate the last refresh time of everything news data.
     * */
    const val KEY_EVERYTHING_TIME_DURATION = "key_everything_time_duration"

    /**
     * Indicate the last refresh time of top-headline news data.
     * */
    const val KEY_TOP_HEADLINE_TIME_DURATION = "key_top_headline_time_duration"

    /**
     * Indicate the last fetch status of everything news data.
     *
     * Available status:
     *
     * - Success
     * - Success without data
     * - Failed
     * */
    const val KEY_EVERYTHING_STATUS = "key_everything_status"

    /**
     * Indicate the last fetch status of top-headline news data.
     *
     * Available status:
     *
     * - Success
     * - Success without data
     * - Failed
     * */
    const val KEY_TOP_HEADLINE_STATUS = "key_top_headline_status"
}