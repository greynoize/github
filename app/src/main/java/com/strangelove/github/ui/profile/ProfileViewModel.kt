package com.strangelove.github.ui.profile

import android.annotation.SuppressLint
import androidx.databinding.Bindable
import com.strangelove.github.BR
import com.strangelove.github.base.BaseViewModel
import com.strangelove.github.data.model.profile.Profile
import com.strangelove.github.data.network.NetworkCallbackWrapper
import com.strangelove.github.data.network.test.GithubRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ProfileViewModel(private val githubRepository: GithubRepository) : BaseViewModel() {
    private var mProfile: Profile? = null

    fun setProfile(profile: Profile) {
        mProfile = profile
        notifyPropertyChanged(BR.profile)
        notifyChange()
    }

    @Bindable
    fun getProfile() = mProfile

    @SuppressLint("CheckResult")
    fun loadProfile() {
        if (!mLoading) {
            githubRepository.getProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : NetworkCallbackWrapper<Response<Profile>>(this) {
                    override fun onSubscribe(d: Disposable) {
                        super.onSubscribe(d)
                        mError = false
                        mLoading = true

                        notifyPropertyChanged(BR.profileVisible)
                    }

                    override fun onSuccess(t: Response<Profile>) {
                        mLoading = false
                        val response = t.body()

                        if (response != null) {
                            setProfile(response)
                        }

                        notifyPropertyChanged(BR.profileVisible)
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        mLoading = false
                        notifyPropertyChanged(BR.profileVisible)
                    }
                })
        }
    }

    @Bindable
    fun isProfileVisible() = !isLoading() && !isError()
}