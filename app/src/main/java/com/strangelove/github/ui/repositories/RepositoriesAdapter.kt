package com.strangelove.github.ui.repositories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.strangelove.github.R
import com.strangelove.github.data.model.repository.RepositoryInfo
import com.strangelove.github.databinding.RepositoriesItemLayoutBinding
import org.jetbrains.anko.sdk27.coroutines.onClick

class RepositoriesAdapter(private var repositoriesItems: MutableList<RepositoryInfo?>, private val onRepoClick: (RepositoryInfo) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var viewHolder: RecyclerView.ViewHolder

        when (viewType) {
            ITEM -> {
                viewHolder = ItemViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.repositories_item_layout, parent, false))
            }
            LOADER -> {
                viewHolder = LoadingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.repositories_item_loading_layout, parent, false))
            }
        }

        return viewHolder
    }

    override fun getItemCount() = repositoriesItems.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = with(holder.itemView) {
        when (getItemViewType(position)) {
            ITEM -> {
                val item = repositoriesItems[position]
                (holder as ItemViewHolder).binding.item = item

                onClick {
                    onRepoClick(item!!)
                }
            }

            LOADER -> {

            }
        }
    }

    override fun getItemViewType(position: Int) = if (repositoriesItems[position] == null) ITEM else LOADER

    class ItemViewHolder(var binding: RepositoriesItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun setRepositoriesItems(repositoriesItems: MutableList<RepositoryInfo?>) {
        removeLoadingFooter()
        this.repositoriesItems = repositoriesItems
        notifyDataSetChanged()
    }

    private fun add(it: RepositoryInfo?) {
        if (it != null) {
            repositoriesItems.add(it)
        } else {
            repositoriesItems.add(null)
        }

        notifyItemInserted(repositoriesItems.size - 1)
    }

    fun addLoadingFooter() {
        add(null)
    }

    private fun removeLoadingFooter() {
        if (!repositoriesItems.isEmpty()) {
            val position = repositoriesItems.size - 1
            val item = repositoriesItems[position]

            if (item == null) {
                repositoriesItems.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }

    private companion object {
        private const val ITEM = 0
        private const val LOADER = 1
    }
}