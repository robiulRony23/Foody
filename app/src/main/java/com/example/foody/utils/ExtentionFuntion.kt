package com.example.foody.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(value: T) {
            removeObserver(this)
            observer.onChanged(value)
        }
    })
}

fun <Boolean> LiveData<Boolean>.observeTrueOnce(lifecycleOwner: LifecycleOwner, observer: Observer<Boolean>) {
    observe(lifecycleOwner, object : Observer<Boolean> {
        override fun onChanged(value: Boolean) {
            if(value!!.equals(true)) {
                removeObserver(this)
            }

            observer.onChanged(value)
        }
    })
}