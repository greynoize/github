package com.strangelove.cadastre.data.network

import com.strangelove.cadastre.base.BaseViewModel
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

abstract class NetworkCallbackWrapper<T : Response<*>>(private val viewModel: BaseViewModel) : SingleObserver<T> {
    override fun onError(e: Throwable) {
        when (e) {
            is HttpException -> viewModel.onResponseError(getError(e))
            is SocketTimeoutException -> viewModel.onTimeoutError()
            is IOException -> viewModel.onNetworkError()
            else -> viewModel.onUnknownError()
        }
    }

    override fun onSubscribe(d: Disposable) {}

    private fun getError(httpException: HttpException): ErrorResponse {
        return ErrorResponse(httpException.response()?.code(), httpException.response()?.message())
    }
}