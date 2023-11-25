package io.core.datastore.preference_keys

object UserPreferenceKeys {

    /**
     * A tag that identifies whether the App has shown what the user needs to know.
     * */
    const val KEY_IS_SHOW_USER_AGREEMENT = "key_is_show_user_agreement"

    /**
     * A marker used to identify whether the user agrees to the user terms.
     * */
    const val KEY_IS_AGREE_USER_TERMS = "key_is_agree_user_terms"

    /**
     * A marker used to identify whether the user agrees to the privacy agreement.
     * */
    const val KEY_IS_AGREE_USER_PRIVACY = "key_is_agree_user_privacy"
}