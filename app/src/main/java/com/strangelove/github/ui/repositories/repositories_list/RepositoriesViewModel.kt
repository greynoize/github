package com.strangelove.github.ui.repositories.repositories_list

import android.annotation.SuppressLint
import androidx.databinding.Bindable
import com.strangelove.github.BR
import com.strangelove.github.base.BaseViewModel
import com.strangelove.github.data.model.repository.RepositoryInfo
import com.strangelove.github.data.network.NetworkCallbackWrapper
import com.strangelove.github.data.network.test.GithubRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class RepositoriesViewModel(private val githubRepository: GithubRepository) : BaseViewModel() {
    private var mRepositoriesList = mutableListOf<RepositoryInfo?>()
        set (value) {
            mRepositoriesList.addAll(value)
            notifyPropertyChanged(BR.repositoriesList)
        }

    @Bindable
    fun getRepositoriesList() = mRepositoriesList

    @SuppressLint("CheckResult")
    fun requestRepositories() {
        if (!mLoading) {
            val lastItemId = if (mRepositoriesList.isEmpty()) 0 else mRepositoriesList.last()!!.id

            githubRepository.getRepositories(lastItemId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : NetworkCallbackWrapper<Response<MutableList<RepositoryInfo?>>>(this) {
                    override fun onSubscribe(d: Disposable) {
                        super.onSubscribe(d)
                        mError = false
                        mLoading = true
                    }

                    override fun onSuccess(t: Response<MutableList<RepositoryInfo?>>) {
                        mLoading = false
                        val response = t.body()

                        if (response != null) {
                            mRepositoriesList = response
                            notifyPropertyChanged(BR.repositoriesList)
                        }
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        mLoading = false
                    }
                })
        }
    }
}