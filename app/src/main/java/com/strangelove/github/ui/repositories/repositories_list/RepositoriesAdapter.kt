package com.strangelove.github.ui.repositories.repositories_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.strangelove.github.R
import com.strangelove.github.data.model.repository.RepositoryInfo
import com.strangelove.github.databinding.RepositoriesItemLayoutBinding
import org.jetbrains.anko.sdk27.coroutines.onClick

class RepositoriesAdapter(private var repositoriesItems: MutableList<RepositoryInfo?>, private val onRepoClick: (RepositoryInfo) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
                holder.binding.executePendingBindings()

                onClick {
                    onRepoClick(item!!)
                }
            }
        }
    }

    override fun getItemViewType(position: Int) = if (repositoriesItems[position] != null) ITEM else LOADER

    inner class ItemViewHolder(var binding: RepositoriesItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun setRepositoriesItems(repositoriesItems: MutableList<RepositoryInfo?>) {
        val positionStart = this.repositoriesItems.size
        this.repositoriesItems = repositoriesItems

        notifyItemRangeInserted(positionStart, repositoriesItems.size - positionStart)
    }

    fun addLoadingFooter() {
        if (repositoriesItems.isEmpty() || repositoriesItems.last() != null) {
            repositoriesItems.add(null)
            notifyItemInserted(repositoriesItems.size - 1)
        }
    }

    fun removeLoadingFooter() {
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