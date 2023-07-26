package io.dev.relic.domain.nav_type

import android.os.Bundle
import androidx.navigation.NavType
import com.squareup.moshi.Moshi
import io.dev.relic.domain.model.todo.TodoDataModel

/**
 * @see TodoDataModel
 * */
class TodoDataNavType : NavType<TodoDataModel>(isNullableAllowed = false) {

    /**
     * Get a value of this type from the `bundle`
     *
     * @param bundle bundle to get value from
     * @param key    bundle key
     * @return value of this type
     */
    override fun get(
        bundle: Bundle,
        key: String
    ): TodoDataModel? {
        return bundle.getParcelable(key)
    }

    /**
     * Parse a value of this type from a String.
     *
     * @param value string representation of a value of this type
     * @return parsed value of the type represented by this NavType
     * @throws IllegalArgumentException if value cannot be parsed into this type
     */
    override fun parseValue(value: String): TodoDataModel {
        return Moshi.Builder()
            .build()
            .adapter(TodoDataModel::class.java)
            .fromJson(value) ?: TodoDataModel.emptyModel()
    }

    /**
     * Put a value of this type in the `bundle`
     *
     * @param bundle bundle to put value in
     * @param key    bundle key
     * @param value  value of this type
     */
    override fun put(
        bundle: Bundle,
        key: String,
        value: TodoDataModel
    ) {
        bundle.putParcelable(
            /* key = */ key,
            /* value = */ value
        )
    }

}