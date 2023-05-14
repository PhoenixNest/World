package io.dev.relic.core.module.data.database

import androidx.annotation.IntDef

@IntDef(OrderType.ASC, OrderType.DESC)
@Retention(AnnotationRetention.SOURCE)
annotation class OrderType {
    companion object {
        const val ASC = 0
        const val DESC = 1
    }
}
