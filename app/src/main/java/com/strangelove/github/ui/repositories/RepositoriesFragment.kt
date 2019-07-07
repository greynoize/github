package com.strangelove.github.ui.repositories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.strangelove.github.BR
import com.strangelove.github.R
import kotlinx.android.synthetic.main.repositories_layout.*
import org.koin.android.architecture.ext.viewModel
import com.strangelove.github.databinding.RepositoriesLayoutBinding

class RepositoriesFragment: Fragment() {
    private val baseViewModel: RepositoriesViewModel by viewModel()
    lateinit var adapter: RepositoriesAdapter
    lateinit var layoutManager: LinearLayoutManager
    lateinit var onPropertyChangedCallback: Observable.OnPropertyChangedCallback

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: RepositoriesLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.repositories_layout, container, false)
        binding.viewModel = baseViewModel

        onPropertyChangedCallback = object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                when (propertyId) {
                    BR.repositoriesList -> {
                        adapter.setRepositoriesItems(baseViewModel.getRepositoriesList())
                    }
                }
            }
        }

        baseViewModel.addOnPropertyChangedCallback(onPropertyChangedCallback)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        adapter = RepositoriesAdapter(baseViewModel.getRepositoriesList()) {

        }

        repositories_recyclerView.adapter = adapter
        repositories_recyclerView.layoutManager = layoutManager
        repositories_recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItemsCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val isEndOfList = lastVisibleItemPosition + BORDER_ITEMS_COUNT >= totalItemsCount

                if (totalItemsCount > 0 && isEndOfList) {
                    adapter.addLoadingFooter()
                }
            }
        })

        baseViewModel.requestRepositories()

    }

    override fun onDestroy() {
        baseViewModel.removeOnPropertyChangedCallback(onPropertyChangedCallback)
        super.onDestroy()
    }

    private companion object {
        private const val BORDER_ITEMS_COUNT = 3
    }
}