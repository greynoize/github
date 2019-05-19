package com.strangelove.cadastre.ui.main

import android.databinding.Bindable
import com.strangelove.cadastre.BR
import com.strangelove.cadastre.base.BaseViewModel
import com.strangelove.cadastre.data.network.ApiErrorResponse
import com.strangelove.cadastre.data.network.ApiSuccessResponse
import com.strangelove.cadastre.data.network.test.TestRepository

class MainViewModel(private val testRepository: TestRepository): BaseViewModel() {
    var mClick = true
    set(value) {
        field = value
        notifyPropertyChanged(BR.click)
    }

    @Bindable
    fun getClick() = mClick

    fun onClick() {
        testRepository.getCities().observeForever {
            when (it) {
                is ApiSuccessResponse -> {
                    mClick = true
                }

                is ApiErrorResponse -> {

                }
            }
        }
    }
}