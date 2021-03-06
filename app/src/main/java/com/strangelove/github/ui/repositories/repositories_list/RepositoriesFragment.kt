package com.strangelove.github.ui.repositories.repositories_list

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
import com.strangelove.github.ui.repositories.repository_info.RepositoryActivity

class RepositoriesFragment : Fragment() {
    private val reposViewModel: RepositoriesViewModel by viewModel()
    private lateinit var adapter: RepositoriesAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var onPropertyChangedCallback: Observable.OnPropertyChangedCallback

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: RepositoriesLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.repositories_layout, container, false)
        binding.viewModel = reposViewModel

        onPropertyChangedCallback = object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                when (propertyId) {
                    BR.repositoriesList -> {
                        adapter.setRepositoriesItems(reposViewModel.getRepositoriesList())
                    }

                    BR.loading -> {
                        if (reposViewModel.isLoading()) {
                            adapter.addLoadingFooter()
                        } else {
                            adapter.removeLoadingFooter()
                        }
                    }
                }
            }
        }

        reposViewModel.addOnPropertyChangedCallback(onPropertyChangedCallback)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        adapter = RepositoriesAdapter(reposViewModel.getRepositoriesList()) {
            startActivity(RepositoryActivity.getRepositoryActivityIntent(activity!!, it))
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
                    reposViewModel.requestRepositories()
                }
            }
        })

        reposViewModel.requestRepositories()
    }

    override fun onDestroy() {
        reposViewModel.removeOnPropertyChangedCallback(onPropertyChangedCallback)
        super.onDestroy()
    }

    private companion object {
        private const val BORDER_ITEMS_COUNT = 1
    }
}