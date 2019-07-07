package com.strangelove.cadastre.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import com.strangelove.cadastre.BR
import com.strangelove.cadastre.data.network.ErrorResponse
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel: ViewModel(), Observable, LifecycleObserver {
    @Transient
    private var mCallbacks: PropertyChangeRegistry? = null

    protected var mLoading: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.loading)
        }

    @Bindable
    fun isLoading(): Boolean = mLoading

    @Bindable
    fun isNetworkError() = true

    @Bindable
    fun isTimeoutError() = true

    @Bindable
    fun isUnknownError() = true

    @Bindable
    fun isResponseError() = true

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

    open fun onResponseError(error: ErrorResponse) {
        notifyPropertyChanged(BR.responseError)
    }

    fun onNetworkError() {
        notifyPropertyChanged(BR.networkError)
    }

    fun onTimeoutError() {
        notifyPropertyChanged(BR.timeoutError)
    }

    fun onUnknownError() {
        notifyPropertyChanged(BR.unknownError)
    }
}