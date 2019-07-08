package com.strangelove.github.ui.repositories.repository_info

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import com.strangelove.github.BR
import com.strangelove.github.R
import com.strangelove.github.data.model.repository.RepositoryInfo
import com.strangelove.github.databinding.RepositoryInfoLayoutBinding
import kotlinx.android.synthetic.main.repository_info_layout.*
import org.koin.android.architecture.ext.viewModel

class RepositoryActivity : AppCompatActivity() {
    private val repositoryViewModel: RepositoryViewModel by viewModel()
    private lateinit var onPropertyChangedCallback: Observable.OnPropertyChangedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.repositories_layout)

        val binding: RepositoryInfoLayoutBinding = DataBindingUtil.setContentView(this, R.layout.repository_info_layout)
        binding.viewModel = repositoryViewModel

        onPropertyChangedCallback = object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                when (propertyId) {
                    BR.openRepoInBrowser -> {
                        startActivity(
                            Intent(Intent.ACTION_VIEW, Uri.parse(repositoryViewModel.getItem()?.html_url))
                        )
                    }
                }
            }
        }

        repositoryViewModel.addOnPropertyChangedCallback(onPropertyChangedCallback)

        repositoryViewModel.setItem(intent.getParcelableExtra(REPOSITORY_INFO))

        repositoryInfo_link.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    override fun onStop() {
        repositoryViewModel.removeOnPropertyChangedCallback(onPropertyChangedCallback)
        super.onStop()
    }

    companion object {
        fun getRepositoryActivityIntent(activity: Activity, repositoryInfo: RepositoryInfo): Intent {
            return Intent(activity, RepositoryActivity::class.java).apply {
                putExtra(REPOSITORY_INFO, repositoryInfo)
            }
        }

        private const val REPOSITORY_INFO = "REPOSITORY_INFO"
    }
}