package com.strangelove.github.ui.profile

import android.annotation.SuppressLint
import androidx.databinding.Bindable
import com.strangelove.github.BR
import com.strangelove.github.base.BaseViewModel
import com.strangelove.github.data.model.profile.Profile
import com.strangelove.github.data.network.NetworkCallbackWrapper
import com.strangelove.github.data.network.github.GithubRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

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
                    }

                    override fun onSuccess(t: Response<Profile>) {
                        mLoading = false
                        val response = t.body()

                        if (response != null) {
                            setProfile(response)
                        }
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        mLoading = false
                    }
                })
        }
    }

    fun isProfileVisible() = !isLoading() && !isError()
}