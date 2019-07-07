package com.strangelove.github.ui.main

import android.annotation.SuppressLint
import androidx.databinding.Bindable
import com.strangelove.github.BR
import com.strangelove.github.base.BaseViewModel
import com.strangelove.github.data.model.network.City
import com.strangelove.github.data.network.NetworkCallbackWrapper
import com.strangelove.github.data.network.test.GithubRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class MainViewModel(private val githubRepository: GithubRepository): BaseViewModel() {
    private var mClick = true
    set(value) {
        field = value
        notifyPropertyChanged(BR.click)
    }

    @Bindable
    fun getClick() = mClick

    @SuppressLint("CheckResult")
    fun onClick() {
        githubRepository.getCities()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: NetworkCallbackWrapper<Response<MutableList<City>>>(this) {
                override fun onSuccess(t: Response<MutableList<City>>) {

                }
            })
    }
}