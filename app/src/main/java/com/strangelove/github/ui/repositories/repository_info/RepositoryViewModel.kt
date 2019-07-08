package com.strangelove.github.ui.repositories.repository_info

import androidx.databinding.Bindable
import com.strangelove.github.BR
import com.strangelove.github.base.BaseViewModel
import com.strangelove.github.data.model.repository.RepositoryInfo

class RepositoryViewModel: BaseViewModel() {
    private var mItem: RepositoryInfo? = null

    @Bindable
    fun getItem() = mItem

    fun setItem(item: RepositoryInfo) {
        mItem = item
        notifyPropertyChanged(BR.item)
    }

    fun onLinkClick() {
        notifyPropertyChanged(BR.openRepoInBrowser)
    }

    @Bindable
    fun getOpenRepoInBrowser()  = true
}