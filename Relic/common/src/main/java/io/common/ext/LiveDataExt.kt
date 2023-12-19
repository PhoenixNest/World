package io.common.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

object LiveDataExt {

    fun <T> LiveData<T>.observeOnce(observer: Observer<T>) {
        observeForever(
            object : Observer<T> {
                override fun onChanged(value: T) {
                    removeObserver(this)
                    observer.onChanged(value)
                }
            }
        )
    }

    fun <T> LiveData<T>.observeOnce(
        lifecycleOwner: LifecycleOwner,
        observer: Observer<T>
    ) {
        observe(
            /* owner = */ lifecycleOwner,
            /* observer = */ object : Observer<T> {
                override fun onChanged(value: T) {
                    removeObserver(this)
                    observer.onChanged(value)
                }
            }
        )
    }

}