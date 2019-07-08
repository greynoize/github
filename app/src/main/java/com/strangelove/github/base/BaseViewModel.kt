package com.strangelove.github.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import com.strangelove.github.BR
import com.strangelove.github.data.network.ErrorResponse

abstract class BaseViewModel: ViewModel(), Observable, LifecycleObserver {
    @Transient
    private var mCallbacks: PropertyChangeRegistry? = null

    protected var mLoading: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.loading)
        }

    protected var mError: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.error)
        }

    @Bindable
    fun isLoading(): Boolean = mLoading

    @Bindable
    fun isError() = mError

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        synchronized(this) {
            if (mCallbacks == null) {
                mCallbacks = PropertyChangeRegistry()
            }
        }
        mCallbacks!!.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks!!.remove(callback)
    }

    fun notifyChange() {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks!!.notifyCallbacks(this, 0, null)
    }

    fun notifyPropertyChanged(fieldId: Int) {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks!!.notifyCallbacks(this, fieldId, null)
    }

    // Every error type can be specified here, but for the simplicity in this test project was created just a one error var

    open fun onResponseError(error: ErrorResponse) {
        mError = true
    }

    fun onNetworkError() {
        mError = true
    }

    fun onTimeoutError() {
        mError = true
    }

    fun onUnknownError() {
        mError = true
    }
}